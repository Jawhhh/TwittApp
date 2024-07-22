package by.jawh.subscribeservice.exception.handler;

import by.jawh.subscribeservice.exception.ProfileAlreadyExistsException;
import by.jawh.subscribeservice.exception.ProfileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<?> profileNotFoundHandle(ProfileNotFoundException exception) {
        log.info("handle profile not found exception");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<?> profileAlreadyExistsHandle(ProfileAlreadyExistsException exception) {
        log.info("handle profile already exists exception");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.getMessage());
    }
}
