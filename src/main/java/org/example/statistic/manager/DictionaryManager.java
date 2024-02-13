package org.example.statistic.manager;

import lombok.Getter;
import lombok.Setter;
import org.example.entities.Category;
import org.example.entities.PaymentMethod;
import org.example.exception.DictionaryValueAlreadyExistException;
import org.example.exception.DictionaryValueNotExistException;

import java.util.List;
@Getter
public class DictionaryManager {
    private Dictionary<Category> categoryDictionary;
    private Dictionary<PaymentMethod> paymentMethodDictionary;

    public DictionaryManager(Dictionary<Category> categoryDictionary, Dictionary<PaymentMethod> paymentMethodDictionary) {
        this.categoryDictionary = categoryDictionary;
        this.paymentMethodDictionary = paymentMethodDictionary;
    }

}
