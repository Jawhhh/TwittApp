package by.jawh.subscribeservice.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProfileNotFoundException extends RuntimeException {

    private String message;
    private Throwable throwable;

    public ProfileNotFoundException(String message) {
        this.message = message;
    }
}
