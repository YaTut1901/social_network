package training.tasks.transactions.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import training.tasks.transactions.model.User;
import training.tasks.transactions.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static training.tasks.transactions.constant.TestConstants.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class UserServiceImplTest {
    @Autowired
    private UserService userService;
    private Long bobId;
    private Long johnId;

    @BeforeAll
    public void init() {
        johnId = userService.save(new User(JOHN, JOHN_EMAIL)).getId();
        bobId = userService.save(new User(BOB, BOB_EMAIL)).getId();
    }

    @Test
    void followUser() {
        userService.followUser(johnId, bobId);
        User john = userService.getOne(johnId);
        User bob = userService.getOne(bobId);
        assertTrue(john.getSubscriptions().contains(bob));
    }
}