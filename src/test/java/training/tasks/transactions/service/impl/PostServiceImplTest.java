package training.tasks.transactions.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import training.tasks.transactions.model.Post;
import training.tasks.transactions.model.User;
import training.tasks.transactions.service.PostService;
import training.tasks.transactions.service.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static training.tasks.transactions.constant.TestConstants.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PostServiceImplTest {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    private Long postId;

    @BeforeAll
    public void init() {
        User john = userService.save(new User(JOHN, JOHN_EMAIL));
        postId = postService.save(new Post(POST_CONTENT, john, 0, LocalDateTime.now())).getId();
    }

    @Test
    public void likePostTest() {
        Post post = postService.likePost(postId);
        Integer likes = postService.getOne(postId).getLikes();
        assertTrue(likes == 1);
    }
}