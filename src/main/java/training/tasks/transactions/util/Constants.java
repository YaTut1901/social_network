package training.tasks.transactions.util;

import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static final String EMAIL_REGEX = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    public static final String TELEGRAM_REGEX = "^@.+";
    public static final String INAPPROPRIATE_INFO_MESSAGE = "{ \"message\" : \"Inappropriate info. Please check input for bad language\" }";
    public static final String INVALID_INFO_MESSAGE = "{ \"message\" : \"Invalid info\" }";
    public static final String ABSENT_ENTITY_MESSAGE = "{ \"message\" : \"No such entity exist\" }";
    public static final String INVALID_INPUT_FORMAT_MESSAGE = "{ \"message\" : \"Invalid input format. Check if name is between 5 and 20 characters long, name and email not blank\" }";
}
