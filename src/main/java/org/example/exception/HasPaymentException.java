package org.example.exception;

public class HasPaymentException extends RuntimeException {
    private Object value;

    public <T> HasPaymentException(T value) {
        super("The value " + value + " has payment");
        this.value = value;
    }
}
