package org.example.exception;

public class ValueAlreadyExistException extends RuntimeException {
    private Object value;

    public <T> ValueAlreadyExistException(T value) {
        super("The value " + value + " is already exist");
        this.value = value;
    }
}
