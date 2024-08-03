package by.jawh.subscribeservice.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionNotFoundException extends RuntimeException {

    private String message;
    private Throwable throwable;

    public SubscriptionNotFoundException(String message) {
        this.message = message;
    }
}
