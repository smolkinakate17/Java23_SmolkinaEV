package org.example.exception;

public class DictionaryValueNotExistException extends IndexOutOfBoundsException{
    public <T> DictionaryValueNotExistException(T value){
        super("Can not find the value "+ value+" in this list");
    }
}
