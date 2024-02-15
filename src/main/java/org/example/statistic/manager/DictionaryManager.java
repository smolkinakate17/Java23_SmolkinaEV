package org.example.statistic.manager;


import org.example.entities.Category;
import org.example.entities.PaymentMethod;


public record DictionaryManager(Dictionary<Category> categoryDictionary,
                                Dictionary<PaymentMethod> paymentMethodDictionary) {

}
