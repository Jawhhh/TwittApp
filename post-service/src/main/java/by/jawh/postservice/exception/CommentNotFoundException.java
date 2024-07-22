package by.jawh.postservice.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CommentNotFoundException extends RuntimeException {

    private String message;
    private Throwable throwable;

    public CommentNotFoundException(String message) {
        this.message = message;
    }
}
