package training.tasks.transactions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import training.tasks.transactions.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteById(Long id);

    @Override
    boolean existsById(Long aLong);

    @Query("select u from user u where :user member of u.subscriptions")
    List<User> findFollowers(User user);
}
