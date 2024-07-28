package by.jawh.postservice.exception.handler;

import by.jawh.postservice.exception.CommentNotFoundException;
import by.jawh.postservice.exception.PostNotFoundException;
import by.jawh.postservice.exception.ProfileIdNotValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> postNotFound(PostNotFoundException exception) {
        log.info("throw post not found exception");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ProfileIdNotValidException.class)
    public ResponseEntity<?> profileIdNotValid(ProfileIdNotValidException exception) {
        log.info("throw profile id not valid, you can't change or delete other people's data");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<?> commentNotFound(CommentNotFoundException exception) {
        log.info("throw comment not found exception");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> somethingHappened(Exception exception) {
        log.info(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }
}
