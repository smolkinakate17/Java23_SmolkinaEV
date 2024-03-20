package org.example.statistic.manager;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.entities.PaymentCategory;
import org.example.entities.PaymentMethod;
import org.example.exception.ValueNotExistException;
import org.example.statistic.SqlScript;
import org.example.statistic.data.PaymentManagerData;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentManagerTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private List<Payment> allPayments;
    private PaymentManager paymentManager;

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
        allPayments = entityManager.createQuery(
                "from Payment p", Payment.class
        ).getResultList();
        paymentManager = new PaymentManager(entityManager);
    }

    @AfterEach
    public void closeSession() throws IOException {
        if (entityManager != null) {
            entityManager.close();
        }
        SqlScript.runFromFile(entityManagerFactory, "DeleteAll.sql");
    }

    @Test
    public void testGetPaymentListByDate() {
        LocalDate januaryFirst = LocalDate.of(2024, 1, 1);
        assertEquals(PaymentManagerData.paymentListByDate(januaryFirst, allPayments), paymentManager.getPaymentListByDate(januaryFirst));
        LocalDate februaryFirst = LocalDate.of(2024, 2, 1);
        assertEquals(PaymentManagerData.paymentListByDate(februaryFirst, allPayments), paymentManager.getPaymentListByDate(februaryFirst));
        LocalDate notInDB = LocalDate.of(2022, 1, 1);
        assertEquals(PaymentManagerData.paymentListByDate(notInDB, allPayments), paymentManager.getPaymentListByDate(notInDB));
    }

    @Test
    public void testAdd() {
        Payment payment = new Payment(LocalDateTime.of(2024, 1, 1, 1, 1), "test", BigDecimal.ONE);
        allPayments.add(payment);
        paymentManager.add(payment);
        List<Payment> afterAdd = entityManager.createQuery("from Payment p", Payment.class).getResultList();
        assertEquals(allPayments.size(), afterAdd.size());
    }

    @Test
    public void testUpdate() {
        Payment payment = allPayments.get(allPayments.size() - 1);
        payment.setSupplier("newTest");
        paymentManager.update(payment);
        List<Payment> afterUpdate = entityManager.createQuery("from Payment p", Payment.class).getResultList();
        assertTrue(afterUpdate.contains(payment));
    }

    @Test
    public void testDelete() {
        Payment payment = allPayments.get(allPayments.size() - 1);
        paymentManager.delete(payment);
        List<Payment> afterDelete = entityManager.createQuery("from Payment p", Payment.class).getResultList();
        assertFalse(afterDelete.contains(payment));

        Payment bad = new Payment();
        Assertions.assertThrows(ValueNotExistException.class, () ->
                paymentManager.delete(bad));

    }

}