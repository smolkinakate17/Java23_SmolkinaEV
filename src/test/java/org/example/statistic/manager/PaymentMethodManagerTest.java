package org.example.statistic.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.entities.PaymentCategory;
import org.example.entities.PaymentMethod;
import org.example.exceptions.ValueAlreadyExistException;
import org.example.exceptions.ValueNotExistException;
import org.example.statistic.SqlScript;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentMethodManagerTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private List<PaymentMethod> allPaymentMethodList;
    PaymentMethodManager paymentMethodManager;

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
        allPaymentMethodList = entityManager.createQuery(
                        "from PaymentMethod p"
                        , PaymentMethod.class
                )
                .getResultList();
        paymentMethodManager = new PaymentMethodManager(entityManager);
    }

    @AfterEach
    public void closeSession() throws IOException {
        if (entityManager != null) {
            entityManager.close();
        }
        SqlScript.runFromFile(entityManagerFactory, "DeleteAll.sql");
    }

    @Test
    void add() {
        PaymentMethod m = new PaymentMethod("test");
        allPaymentMethodList.add(m);
        m = paymentMethodManager.add(m);
        List<PaymentMethod> afterAddPaymentMethod = entityManager.createQuery("from PaymentMethod p", PaymentMethod.class).getResultList();
        assertEquals(allPaymentMethodList.size(), afterAddPaymentMethod.size());
        assertTrue(afterAddPaymentMethod.contains(m));
        PaymentMethod badPaymentMethod = afterAddPaymentMethod.get(afterAddPaymentMethod.size() - 1);
        Assertions.assertThrows(ValueAlreadyExistException.class, () ->
                paymentMethodManager.add(badPaymentMethod));
    }

    @Test
    void update() {
        PaymentMethod m = allPaymentMethodList.get(allPaymentMethodList.size() - 1);
        m.setTitle("newTestTitle");
        paymentMethodManager.update(m);
        List<PaymentMethod> afterUpdatePaymentMethod = entityManager.createQuery("from PaymentMethod p", PaymentMethod.class).getResultList();
        assertTrue(afterUpdatePaymentMethod.contains(m));

        PaymentMethod badPaymentMethod = new PaymentMethod();
        Assertions.assertThrows(ValueNotExistException.class, () ->
                paymentMethodManager.update(badPaymentMethod));
    }

    @Test
    void delete() {
        PaymentMethod m = new PaymentMethod("test");
        paymentMethodManager.add(m);
        allPaymentMethodList = entityManager.createQuery(
                        "from PaymentMethod p"
                        , PaymentMethod.class
                )
                .getResultList();
        allPaymentMethodList.remove(m);
        paymentMethodManager.delete(m);
        List<PaymentMethod> afterDeletePaymentMethod = entityManager.createQuery("from PaymentMethod p", PaymentMethod.class).getResultList();
        assertFalse(afterDeletePaymentMethod.contains(m));

        PaymentMethod havePayments = paymentMethodManager.find(1).get();
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                paymentMethodManager.delete(havePayments));
    }
}