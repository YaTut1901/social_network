package training.tasks.transactions.service;

import training.tasks.transactions.model.Comment;

public interface CommentService extends Service<Comment>{
    Comment likeComment(Long id);
    Comment editComment(Long id, String content);
}
