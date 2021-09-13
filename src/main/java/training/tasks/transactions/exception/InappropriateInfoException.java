package training.tasks.transactions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static training.tasks.transactions.util.Constants.INAPPROPRIATE_INFO_MESSAGE;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = INAPPROPRIATE_INFO_MESSAGE)
public class InappropriateInfoException extends RuntimeException { }
