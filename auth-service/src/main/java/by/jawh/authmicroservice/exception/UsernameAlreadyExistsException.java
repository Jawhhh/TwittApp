package by.jawh.authmicroservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernameAlreadyExistsException extends RuntimeException{

    private String message;
    private Throwable throwable;

    public UsernameAlreadyExistsException(String message) {
        this.message = message;
    }
}
