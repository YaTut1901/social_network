package training.tasks.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.tasks.transactions.model.Post;
import training.tasks.transactions.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteById(Long id);

    @Override
    boolean existsById(Long id);
}
