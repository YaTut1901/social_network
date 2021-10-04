package training.tasks.transactions.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.tasks.transactions.model.Comment;
import training.tasks.transactions.repository.CommentRepository;
import training.tasks.transactions.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository repository;

    @Override
    public Comment getOne(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<Comment> getAll() {
        return repository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public List<Comment> addAll(List<Comment> comments) {
        return repository.saveAll(comments);
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
    public Comment likeComment(Long id) {
        Comment comment = getOne(id);
        comment.setLikes(comment.getLikes() + 1);
        return repository.save(comment);
    }

    @Transactional
    @Override
    public Comment editComment(Long id, String content) {
        Comment comment = getOne(id);
        comment.setContent(content);
        return save(comment);
    }
}
