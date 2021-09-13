package training.tasks.transactions.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import static training.tasks.transactions.util.Constants.ABSENT_ENTITY_MESSAGE;
import static training.tasks.transactions.util.Constants.INVALID_INPUT_FORMAT_MESSAGE;

//TODO: tests for exception handling
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity handleAbsentEntity() {
        return new ResponseEntity(ABSENT_ENTITY_MESSAGE, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolation() {
        return new ResponseEntity(INVALID_INPUT_FORMAT_MESSAGE, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity handleAbsentEntityForDelete() {
        return new ResponseEntity(ABSENT_ENTITY_MESSAGE, HttpStatus.NOT_FOUND);
    }
}
