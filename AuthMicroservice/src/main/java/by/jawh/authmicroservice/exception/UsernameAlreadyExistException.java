package by.jawh.authmicroservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsernameAlreadyExistException extends RuntimeException{

    private String message;
    private Throwable throwable;

    public UsernameAlreadyExistException(String message) {
        this.message = message;
    }
}
