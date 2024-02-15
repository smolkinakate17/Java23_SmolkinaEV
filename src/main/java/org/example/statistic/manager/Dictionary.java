package org.example.statistic.manager;

import org.example.exception.DictionaryValueAlreadyExistException;
import org.example.exception.DictionaryValueNotExistException;

import java.util.List;

public record Dictionary<T>(List<T> list) {

    public void add(T value) throws DictionaryValueAlreadyExistException {
        if (!list.contains(value)) {
            list.add(value);
        } else {
            throw new DictionaryValueAlreadyExistException(value);
        }
    }

    public void update(T oldValue, T newValue) throws DictionaryValueAlreadyExistException, DictionaryValueNotExistException {
        if (list.contains(newValue)) {
            throw new DictionaryValueAlreadyExistException(newValue);
        }
        if (!list.contains(oldValue)) {
            throw new DictionaryValueNotExistException(oldValue);
        }
        int index = list.indexOf(oldValue);
        list.set(index, newValue);
    }

    public void delete(T value) throws DictionaryValueNotExistException {
        if (!list.contains(value)) {
            throw new DictionaryValueNotExistException(value);
        }
        list.remove(value);

    }
}