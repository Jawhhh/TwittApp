package by.jawh.postservice.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BlankPostException extends RuntimeException{

    private String message;
    private Throwable throwable;

    public BlankPostException(String message) {
        this.message = message;
    }
}
