package training.tasks.transactions.service;

import training.tasks.transactions.model.User;

import java.util.List;

public interface UserService extends Service<User>{
    User followUser(Long followerId, Long userId);

    List<User> findFollowers(User user);
}
