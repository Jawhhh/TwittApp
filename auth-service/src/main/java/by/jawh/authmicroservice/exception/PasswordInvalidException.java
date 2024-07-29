package by.jawh.authmicroservice.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PasswordInvalidException extends RuntimeException {

    private String message;
    private Throwable throwable;

    public PasswordInvalidException(String message) {
        this.message = message;
    }
}
