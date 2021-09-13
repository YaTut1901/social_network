package training.tasks.transactions.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import training.tasks.transactions.model.Comment;
import training.tasks.transactions.model.Post;
import training.tasks.transactions.model.User;
import training.tasks.transactions.service.CommentService;
import training.tasks.transactions.service.PostService;
import training.tasks.transactions.service.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static training.tasks.transactions.constant.TestConstants.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class CommentServiceImplTest {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    private Long commentId;

    @BeforeAll
    public void init() {
        User john = userService.save(new User(JOHN, JOHN_EMAIL));
        Post post = postService.save(new Post(POST_CONTENT, john, 0, LocalDateTime.now()));
        commentId = commentService.save(new Comment(COMMENT_CONTENT, john, post, 0, LocalDateTime.now())).getId();
    }

    @Test
    public void likeCommentTest() {
        Comment comment = commentService.likeComment(commentId);
        Integer likes = commentService.getOne(commentId).getLikes();
        assertTrue(likes == 1);
    }

    @Test
    void editCommentTest() {
        commentService.editComment(commentId, NEW_COMMENT_CONTENT);
        Comment comment = commentService.getOne(commentId);
        assertEquals(comment.getContent(), NEW_COMMENT_CONTENT);
    }
}