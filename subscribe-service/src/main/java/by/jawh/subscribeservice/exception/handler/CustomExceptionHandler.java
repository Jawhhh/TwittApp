package by.jawh.subscribeservice.exception.handler;

import by.jawh.subscribeservice.exception.ProfileNotFoundException;
import by.jawh.subscribeservice.exception.SubscriptionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<?> handleProfileNotFoundHandle(ProfileNotFoundException exception) {
        log.info("handled profile not found exception: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleOtherRuntimeException(RuntimeException exception) {
        log.warn("handled some kind of exception: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<?> handleSubscriptionNotFoundException(SubscriptionNotFoundException exception) {
        log.info("handled subscription not found exception: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
