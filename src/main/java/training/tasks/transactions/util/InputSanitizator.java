package training.tasks.transactions.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import training.tasks.transactions.exception.InappropriateInfoException;
import training.tasks.transactions.exception.InvalidInfoException;
import training.tasks.transactions.model.dto.UserDto;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static training.tasks.transactions.util.Constants.*;

//TODO: tests for sanitizing data
@Component
public class InputSanitizator {
    @Value("${blacklist.enabled}")
    private Boolean sanitizationEnabled;
    @Value("${blacklist.path}")
    private String pathToBlacklist;
    private List<String> blacklist;

    @PostConstruct
    public void init() {
        Path path = Paths.get(pathToBlacklist);

        if (Files.exists(path)) {
            try {
                String data = Files.readAllLines(path).get(0);
                blacklist = Arrays.stream(data.replaceAll("\"", "").split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            sanitizationEnabled = false;
        }
    }

    public void checkUserInfo(UserDto dto) {
        isEmailValid(dto.getEmail());
        isTelegramValid(dto.getTelegram());
        if (sanitizationEnabled) {
            isAppropriate(dto.getName());
            isAppropriate(dto.getBio());
            isAppropriate(dto.getOtherContacts());
        }
    }

    private void isEmailValid(String email) {
        if (email != null && !email.matches(EMAIL_REGEX)) {
            throw new InvalidInfoException();
        }
    }

    private void isTelegramValid(String telegram) {
        if (telegram != null && !telegram.matches(TELEGRAM_REGEX)) {
            throw new InvalidInfoException();
        }
    }

    private void isAppropriate(String data) {
        if (data != null && blacklist.stream()
                .anyMatch(word -> data.toLowerCase().contains(word.toLowerCase()))) {
            throw new InappropriateInfoException();
        }
    }
}
