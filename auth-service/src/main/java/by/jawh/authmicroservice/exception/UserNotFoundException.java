package by.jawh.authmicroservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundException extends RuntimeException{

    private String message;
    private Throwable throwable;

    public UserNotFoundException(String message) {
        this.message = message;
    }
}
