package training.tasks.transactions.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import training.tasks.transactions.model.User;
import training.tasks.transactions.repository.UserRepository;
import training.tasks.transactions.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Override
    public User getOne(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> addAll(List<User> users) {
        return repository.saveAll(users);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    @Override
    public User followUser(Long followerId, Long userId) {
        User follower = repository.getById(followerId);
        User user = repository.getById(userId);
        follower.getSubscriptions().add(user);
        repository.save(follower);
        return follower;
    }

    @Override
    public List<User> findFollowers(User user) {
        return repository.findFollowers(user);
    }
}
