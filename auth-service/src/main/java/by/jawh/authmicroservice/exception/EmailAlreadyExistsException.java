package by.jawh.authmicroservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmailAlreadyExistsException extends RuntimeException{

    private String message;
    private Throwable throwable;

    public EmailAlreadyExistsException(String message) {
        this.message = message;
    }
}
