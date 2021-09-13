package training.tasks.transactions.service;

import training.tasks.transactions.model.Post;

public interface PostService extends Service<Post>{
    Post likePost(Long id);
}
