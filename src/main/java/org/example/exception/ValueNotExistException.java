package org.example.exception;

public class ValueNotExistException extends IndexOutOfBoundsException {

    private Object value;

    public <T> ValueNotExistException(T value) {
        super("Can not find the value " + value);
        this.value = value;
    }
}
