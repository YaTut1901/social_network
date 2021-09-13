package training.tasks.transactions.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.tasks.transactions.model.Post;
import training.tasks.transactions.repository.PostRepository;
import training.tasks.transactions.service.PostService;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository repository;

    @Override
    public Post getOne(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<Post> getAll() {
        return repository.findAll();
    }

    @Override
    public Post save(Post post) {
        return repository.save(post);
    }

    @Override
    public List<Post> addAll(List<Post> posts) {
        return repository.saveAll(posts);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    @Override
    public Post likePost(Long id) {
        Post post = getOne(id);
        post.setLikes(post.getLikes() + 1);
        return repository.save(post);
    }
}
