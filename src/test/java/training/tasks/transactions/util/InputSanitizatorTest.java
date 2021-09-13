package training.tasks.transactions.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import training.tasks.transactions.model.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;
import static training.tasks.transactions.constant.TestConstants.*;

@SpringBootTest
class InputSanitizatorTest {

    @Test
    void checkUserInfo_withInvalidBlackListPath() {
        UserDto dto = new UserDto(1L,
                JOHN,
                JOHN_EMAIL,
                JOHN_BIO,
                JOHN_TELEGRAM,
                JOHN_OTHER_CONTACTS);


    }
}