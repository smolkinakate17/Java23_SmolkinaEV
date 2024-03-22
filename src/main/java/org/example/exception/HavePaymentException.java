package org.example.exception;

public class HavePaymentException extends RuntimeException {
    private Object value;

    public <T> HavePaymentException(T value) {
        super("The value " + value + " has payment");
        this.value = value;
    }
}
