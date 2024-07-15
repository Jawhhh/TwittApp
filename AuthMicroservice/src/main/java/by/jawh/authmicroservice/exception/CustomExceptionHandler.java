package by.jawh.authmicroservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(BusyException.class)
//    private String busyHandle(BusyException busyException) {
//        log.warn("Warn - username or email is already occupied");
//
//    }
}
