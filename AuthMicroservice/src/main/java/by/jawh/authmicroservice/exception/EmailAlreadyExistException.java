package by.jawh.authmicroservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailAlreadyExistException extends RuntimeException{

    private String message;
    private Throwable throwable;

    public EmailAlreadyExistException(String message) {
        this.message = message;
    }
}
