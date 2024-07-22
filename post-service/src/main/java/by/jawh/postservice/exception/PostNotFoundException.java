package by.jawh.postservice.exception;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PostNotFoundException extends RuntimeException {

    private String message;
    private Throwable throwable;

    public PostNotFoundException(String message) {
        this.message = message;
    }

}

