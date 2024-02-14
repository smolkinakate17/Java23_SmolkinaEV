package org.example.statistic.manager;

import lombok.Getter;

import org.example.entities.Category;
import org.example.entities.PaymentMethod;

@Getter
public class DictionaryManager {
    private Dictionary<Category> categoryDictionary;
    private Dictionary<PaymentMethod> paymentMethodDictionary;

    public DictionaryManager(Dictionary<Category> categoryDictionary, Dictionary<PaymentMethod> paymentMethodDictionary) {
        this.categoryDictionary = categoryDictionary;
        this.paymentMethodDictionary = paymentMethodDictionary;
    }

}
