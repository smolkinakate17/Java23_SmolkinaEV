package org.example.exception;

public class DictionaryValueNotExistException extends IndexOutOfBoundsException{

    private Object value;
    public <T> DictionaryValueNotExistException(T value){
        super("Can not find the value "+ value+" in this list");
        this.value=value;
    }
}
