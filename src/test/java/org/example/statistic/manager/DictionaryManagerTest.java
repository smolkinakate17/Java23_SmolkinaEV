package org.example.statistic.manager;


import org.example.entities.Category;
import org.example.entities.PaymentMethod;
import org.example.exception.DictionaryValueAlreadyExistException;
import org.example.exception.DictionaryValueNotExistException;
import org.example.statistic.data.CategoryData;
import org.example.statistic.data.PaymentMethodData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DictionaryManagerTest{
    private Dictionary<Category> categoryDictionary;
    private Dictionary<PaymentMethod> paymentMethodDictionary;
    private DictionaryManager dictionaryManager;
    private List<Category> testCategoryList;
    private static Category category;
    private List<PaymentMethod> testPaymentMethodList;
    private static PaymentMethod method;
    private static Category newCategory;
    private static PaymentMethod newMethod;
    @BeforeEach
    public void setup() {
        categoryDictionary = new Dictionary<>(CategoryData.categoryList());
        paymentMethodDictionary = new Dictionary<>(PaymentMethodData.paymentMethodList());
        dictionaryManager = new DictionaryManager(categoryDictionary, paymentMethodDictionary);
        testCategoryList = new ArrayList<>(CategoryData.categoryList());
        category = new Category("Category", "Description", "Color");
        testPaymentMethodList=new ArrayList<>(PaymentMethodData.paymentMethodList());
        method=new PaymentMethod("PaymentMethod");
        newCategory=new Category("NewCategory","NewDescription","newColor");
        newMethod=new PaymentMethod("NewMethod");
    }
    @Test
    public void testAdd() throws DictionaryValueAlreadyExistException {
        dictionaryManager.categoryDictionary().add(category);
        testCategoryList.add(category);
        assertEquals(testCategoryList,dictionaryManager.categoryDictionary().list());

        Assertions.assertThrows(DictionaryValueAlreadyExistException.class,()->
                dictionaryManager.categoryDictionary().add(category));

        dictionaryManager.paymentMethodDictionary().add(method);
        testPaymentMethodList.add(method);
        assertEquals(testPaymentMethodList,dictionaryManager.paymentMethodDictionary().list());

        Assertions.assertThrows(DictionaryValueAlreadyExistException.class,()->
                dictionaryManager.paymentMethodDictionary().add(method));

    }
    @Test
    public void testUpdate() throws DictionaryValueAlreadyExistException, DictionaryValueNotExistException {
        Assertions.assertThrows(DictionaryValueNotExistException.class,()->
                dictionaryManager.categoryDictionary().update(newCategory,newCategory));

        dictionaryManager.categoryDictionary().update(CategoryData.other,newCategory);
        testCategoryList.set(testCategoryList.indexOf(CategoryData.other),newCategory);
        assertEquals(testCategoryList,dictionaryManager.categoryDictionary().list());

        Assertions.assertThrows(DictionaryValueAlreadyExistException.class,()->
                dictionaryManager.categoryDictionary().update(newCategory,newCategory));

        Assertions.assertThrows(DictionaryValueNotExistException.class,()->
                dictionaryManager.paymentMethodDictionary().update(newMethod,newMethod));

        dictionaryManager.paymentMethodDictionary().update(PaymentMethodData.other,newMethod);
        testPaymentMethodList.set(testPaymentMethodList.indexOf(PaymentMethodData.other),newMethod);
        assertEquals(testPaymentMethodList,dictionaryManager.paymentMethodDictionary().list());

        Assertions.assertThrows(DictionaryValueAlreadyExistException.class,()->
                dictionaryManager.paymentMethodDictionary().update(newMethod,newMethod));
    }
    @Test
    public void testDelete(){
        dictionaryManager.paymentMethodDictionary().delete(PaymentMethodData.other);
        testPaymentMethodList.remove(PaymentMethodData.other);
        assertEquals(testPaymentMethodList,dictionaryManager.paymentMethodDictionary().list());

        Assertions.assertThrows(DictionaryValueNotExistException.class,()->
                dictionaryManager.paymentMethodDictionary().delete(newMethod));

        dictionaryManager.categoryDictionary().delete(CategoryData.other);
        testCategoryList.remove(CategoryData.other);

        Assertions.assertThrows(DictionaryValueNotExistException.class,()->
                dictionaryManager.categoryDictionary().delete(newCategory));
    }

}