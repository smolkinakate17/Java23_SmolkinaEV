package org.example.statistic.manager;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.entities.PaymentCategory;
import org.example.entities.PaymentMethod;
import org.example.exception.HasPaymentException;
import org.example.exception.ValueAlreadyExistException;
import org.example.exception.ValueNotExistException;

import org.example.statistic.SqlScript;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryManagerTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private List<Category> allCategoryList;
    private List<PaymentMethod> allPaymentMethodList;
    private DictionaryManager dictionaryManager;

    @BeforeAll
    public static void setup() {
        entityManagerFactory = new Configuration()
                .configure("hibernate-postgres.cfg.xml")
                .addAnnotatedClass(Category.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(PaymentMethod.class)
                .addAnnotatedClass(PaymentCategory.class)
                .buildSessionFactory();
    }

    @AfterAll
    public static void tearDown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    @BeforeEach
    public void openSession() throws IOException {
        SqlScript.runFromFile(entityManagerFactory, "InsertAll.sql");
        entityManager = entityManagerFactory.createEntityManager();
        allCategoryList = entityManager.createQuery(
                        "from Category c"
                        , Category.class
                )
                .getResultList();
        allPaymentMethodList = entityManager.createQuery(
                        "from PaymentMethod p"
                        , PaymentMethod.class
                )
                .getResultList();
        Dictionary<Category> categoryDictionary = new Dictionary<>(entityManager);
        Dictionary<PaymentMethod> paymentMethodDictionary = new Dictionary<>(entityManager);
        dictionaryManager = new DictionaryManager(categoryDictionary, paymentMethodDictionary);
    }

    @AfterEach
    public void closeSession() throws IOException {
        if (entityManager != null) {
            entityManager.close();
        }
        SqlScript.runFromFile(entityManagerFactory, "DeleteAll.sql");
    }

    @Test
    public void testAdd() {
        Category c = new Category("test", "test", "test");
        allCategoryList.add(c);
        dictionaryManager.categoryDictionary().add(c);
        List<Category> afterAddCategory = entityManager.createQuery("from Category c", Category.class).getResultList();
        assertEquals(allCategoryList.size(), afterAddCategory.size());

        PaymentMethod m = new PaymentMethod("test");
        allPaymentMethodList.add(m);
        dictionaryManager.paymentMethodDictionary().add(m);
        List<PaymentMethod> afterAddPaymentMethod = entityManager.createQuery("from PaymentMethod p", PaymentMethod.class).getResultList();
        assertEquals(allPaymentMethodList.size(), afterAddPaymentMethod.size());

        Category badCategory = afterAddCategory.get(afterAddCategory.size() - 1);
        assertThrows(ValueAlreadyExistException.class, () ->
                dictionaryManager.categoryDictionary().add(badCategory));
        PaymentMethod badPaymentMethod = afterAddPaymentMethod.get(afterAddPaymentMethod.size() - 1);
        Assertions.assertThrows(ValueAlreadyExistException.class, () ->
                dictionaryManager.paymentMethodDictionary().add(badPaymentMethod));

    }

    @Test
    public void testUpdate() {
        Category c = allCategoryList.get(allCategoryList.size() - 1);
        c.setTitle("newTestTitle");
        dictionaryManager.categoryDictionary().update(c);
        List<Category> afterUpdateCategory = entityManager.createQuery("from Category c", Category.class).getResultList();
        assertTrue(afterUpdateCategory.contains(c));

        PaymentMethod m = allPaymentMethodList.get(allPaymentMethodList.size() - 1);
        m.setTitle("newTestTitle");
        dictionaryManager.paymentMethodDictionary().update(m);
        List<PaymentMethod> afterUpdatePaymentMethod = entityManager.createQuery("from PaymentMethod p", PaymentMethod.class).getResultList();
        assertTrue(afterUpdatePaymentMethod.contains(m));

        Category badCategory = new Category();
        Assertions.assertThrows(ValueNotExistException.class, () ->
                dictionaryManager.categoryDictionary().update(badCategory));

        PaymentMethod badPaymentMethod = new PaymentMethod();
        Assertions.assertThrows(ValueNotExistException.class, () ->
                dictionaryManager.paymentMethodDictionary().update(badPaymentMethod));
    }

    @Test
    public void testDelete() {
        Category c = new Category("test", "test", "test");
        dictionaryManager.categoryDictionary().add(c);
        allCategoryList = entityManager.createQuery(
                        "from Category c"
                        , Category.class
                )
                .getResultList();
        allCategoryList.remove(c);
        dictionaryManager.categoryDictionary().delete(c);
        List<Category> afterDeleteCategory = entityManager.createQuery("from Category c", Category.class).getResultList();
        assertFalse(afterDeleteCategory.contains(c));

        PaymentMethod m = new PaymentMethod("test");
        dictionaryManager.paymentMethodDictionary().add(m);
        allPaymentMethodList = entityManager.createQuery(
                        "from PaymentMethod p"
                        , PaymentMethod.class
                )
                .getResultList();
        allPaymentMethodList.remove(m);
        dictionaryManager.paymentMethodDictionary().delete(m);
        List<PaymentMethod> afterDeletePaymentMethod = entityManager.createQuery("from PaymentMethod p", PaymentMethod.class).getResultList();
        assertFalse(afterDeletePaymentMethod.contains(m));

        Category badCategory = new Category();
        Assertions.assertThrows(ValueNotExistException.class, () ->
                dictionaryManager.categoryDictionary().delete(badCategory));
        PaymentMethod badPaymentMethod = new PaymentMethod();
        Assertions.assertThrows(ValueNotExistException.class, () ->
                dictionaryManager.paymentMethodDictionary().delete(badPaymentMethod));

        Category existCategory = allCategoryList.get(1);
        Assertions.assertThrows(HasPaymentException.class, () ->
                dictionaryManager.categoryDictionary().delete(existCategory));
        PaymentMethod exist = allPaymentMethodList.get(1);
        Assertions.assertThrows(HasPaymentException.class, () ->
                dictionaryManager.paymentMethodDictionary().delete(exist));

    }

}