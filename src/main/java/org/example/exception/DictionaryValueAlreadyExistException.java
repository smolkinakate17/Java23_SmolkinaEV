package org.example.exception;

public class DictionaryValueAlreadyExistException extends Exception{
    public <T> DictionaryValueAlreadyExistException(T value) {
        super("The value "+value+" is already exist in this list");
    }
}
