package by.jawh.profilemicroservice.exception;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class EmailAlreadyExistException extends RuntimeException {

    private String message;
    private Throwable throwable;

    public EmailAlreadyExistException(String message) {
        this.message = message;
    }
}
