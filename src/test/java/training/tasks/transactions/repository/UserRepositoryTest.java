package training.tasks.transactions.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import training.tasks.transactions.model.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static training.tasks.transactions.constant.TestConstants.*;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    @Transactional
    void deleteByIdTest() {
        User user = new User(JOHN, JOHN_EMAIL);
        Long id = repository.save(user).getId();

        repository.deleteById(id);
        assertFalse(repository.findById(id).isPresent());
    }

    @Test
    @Transactional
    void findUserFollowersTest() {
        User john = new User(JOHN, JOHN_EMAIL);
        User bob = new User(BOB, BOB_EMAIL, BOB_BIO, BOB_TELEGRAM, BOB_OTHER_CONTACTS, List.of(john));
        repository.save(john);
        repository.save(bob);

        assertTrue(repository.findFollowers(john).contains(repository.getById(bob.getId())));
    }

    @Test
    @Transactional
    void findUserFollowers_WithNoFollowersTest() {
        User bob = new User(BOB, BOB_EMAIL, BOB_BIO, BOB_TELEGRAM, BOB_OTHER_CONTACTS, new ArrayList<>());
        repository.save(bob);

        assertTrue(repository.findFollowers(bob).isEmpty());
    }
}