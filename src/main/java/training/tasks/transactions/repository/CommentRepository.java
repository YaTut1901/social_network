package training.tasks.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.tasks.transactions.model.Comment;
import training.tasks.transactions.model.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteById(Long id);

    @Override
    boolean existsById(Long id);
}
