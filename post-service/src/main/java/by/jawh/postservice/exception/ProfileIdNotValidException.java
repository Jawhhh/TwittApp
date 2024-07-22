package by.jawh.postservice.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProfileIdNotValidException extends RuntimeException{

    private String message;
    private Throwable throwable;

    public ProfileIdNotValidException(String message) {
        this.message = message;
    }
}
