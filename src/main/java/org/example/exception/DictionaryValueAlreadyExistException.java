package org.example.exception;

public class DictionaryValueAlreadyExistException extends RuntimeException{
    private Object value;
    public <T> DictionaryValueAlreadyExistException(T value) {
        super("The value "+value+" is already exist in this list");
        this.value=value;
    }
}
