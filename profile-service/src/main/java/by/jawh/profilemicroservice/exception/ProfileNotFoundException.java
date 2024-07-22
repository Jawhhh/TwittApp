package by.jawh.profilemicroservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

//@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProfileNotFoundException extends RuntimeException{

    private String message;
    private Throwable throwable;

    public ProfileNotFoundException(String message) {
        this.message = message;
    }

    public ProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
