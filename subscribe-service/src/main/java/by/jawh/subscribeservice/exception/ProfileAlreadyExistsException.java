package by.jawh.subscribeservice.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileAlreadyExistsException extends RuntimeException {

    private String message;
    private Throwable throwable;

    public ProfileAlreadyExistsException(String message) {
        this.message = message;
    }
}
