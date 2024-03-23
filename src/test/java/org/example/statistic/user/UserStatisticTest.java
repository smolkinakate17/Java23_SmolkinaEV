package org.example.statistic.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.entities.PaymentCategory;
import org.example.entities.PaymentMethod;
import org.example.statistic.SqlScript;
import org.example.statistic.data.*;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserStatisticTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private List<Payment> allPaymentList;
    private List<Payment> januaryPaymentList;
    private List<Payment> februaryPaymentList;
    private List<Payment> notInDBPaymentList;
    private UserStatistic allPaymentsStatistic;
    private UserStatistic januaryPaymentsStatistic;
    private UserStatistic februaryPaymentsStatistic;
    private UserStatistic notInDBPaymentsStatistic;

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
        allPaymentList = entityManager.createQuery(
                        "select p from Payment p", Payment.class
                )
                .getResultList();
        januaryPaymentList = entityManager.createQuery(
                        "select p from Payment  p where p.paymentDateTime between :fromDateTime and :toDateTime"
                        , Payment.class
                )
                .setParameter("fromDateTime", PeriodData.january.getFromDateTime()).setParameter("toDateTime", PeriodData.january.getToDateTime())
                .getResultList();
        februaryPaymentList = entityManager.createQuery(
                        "select  p from Payment p where p.paymentDateTime between :fromDateTime and :toDateTime"
                        , Payment.class
                )
                .setParameter("fromDateTime", PeriodData.february.getFromDateTime()).setParameter("toDateTime", PeriodData.february.getToDateTime())
                .getResultList();
        notInDBPaymentList = entityManager.createQuery(
                        "select  p from Payment p where p.paymentDateTime between :fromDateTime and :toDateTime"
                        , Payment.class
                )
                .setParameter("fromDateTime", PeriodData.notInDB.getFromDateTime()).setParameter("toDateTime", PeriodData.notInDB.getToDateTime())
                .getResultList();
        allPaymentsStatistic = new UserStatistic(PeriodData.allPayments, entityManager);
        januaryPaymentsStatistic = new UserStatistic(PeriodData.january, entityManager);
        februaryPaymentsStatistic = new UserStatistic(PeriodData.february, entityManager);
        notInDBPaymentsStatistic = new UserStatistic(PeriodData.notInDB, entityManager);
    }

    @AfterEach
    public void closeSession() throws IOException {
        if (entityManager != null) {
            entityManager.close();
        }
        SqlScript.runFromFile(entityManagerFactory, "DeleteAll.sql");
    }

    @Test
    public void testGetPaymentCount() {
        assertEquals(100, allPaymentsStatistic.getPaymentCount());
        assertEquals(10, januaryPaymentsStatistic.getPaymentCount());
        assertEquals(12, februaryPaymentsStatistic.getPaymentCount());
        assertEquals(9, new UserStatistic(PeriodData.march, entityManager).getPaymentCount());
        assertEquals(7, new UserStatistic(PeriodData.april, entityManager).getPaymentCount());
        assertEquals(8, new UserStatistic(PeriodData.may, entityManager).getPaymentCount());
        assertEquals(0, notInDBPaymentsStatistic.getPaymentCount());
    }

    @Test
    public void testGetPaymentAmount() {
        assertEquals(BigDecimal.valueOf(521558.63), allPaymentsStatistic.getPaymentAmount());
        assertEquals(BigDecimal.valueOf(61639.41), januaryPaymentsStatistic.getPaymentAmount());
        assertEquals(BigDecimal.valueOf(53832.95), februaryPaymentsStatistic.getPaymentAmount());
        assertEquals(BigDecimal.valueOf(61107.88), new UserStatistic(PeriodData.march, entityManager).getPaymentAmount());
        assertEquals(BigDecimal.valueOf(37906.41), new UserStatistic(PeriodData.april, entityManager).getPaymentAmount());
        assertEquals(BigDecimal.valueOf(44535.31), new UserStatistic(PeriodData.may, entityManager).getPaymentAmount());
        assertEquals(BigDecimal.valueOf(0.0), notInDBPaymentsStatistic.getPaymentAmount());
    }

    @Test
    public void testGetPaymentMethodSharesMap() {
        assertEquals(PaymentMethodShareMapData.mapByStream(allPaymentList, allPaymentsStatistic), allPaymentsStatistic.getPaymentMethodSharesMap());
        assertEquals(PaymentMethodShareMapData.mapByStream(januaryPaymentList, januaryPaymentsStatistic), januaryPaymentsStatistic.getPaymentMethodSharesMap());
        assertEquals(PaymentMethodShareMapData.mapByStream(februaryPaymentList, februaryPaymentsStatistic), februaryPaymentsStatistic.getPaymentMethodSharesMap());
        assertEquals(PaymentMethodShareMapData.mapByStream(notInDBPaymentList, notInDBPaymentsStatistic), notInDBPaymentsStatistic.getPaymentMethodSharesMap());
    }

    @Test
    public void testGetCategorySharesMap() {
        assertEquals(CategoryShareMapData.mapByStream(allPaymentList, allPaymentsStatistic), allPaymentsStatistic.getCategorySharesMap());
        assertEquals(CategoryShareMapData.mapByStream(januaryPaymentList, januaryPaymentsStatistic), januaryPaymentsStatistic.getCategorySharesMap());
        assertEquals(CategoryShareMapData.mapByStream(februaryPaymentList, februaryPaymentsStatistic), februaryPaymentsStatistic.getCategorySharesMap());
        assertEquals(CategoryShareMapData.mapByStream(notInDBPaymentList, notInDBPaymentsStatistic), notInDBPaymentsStatistic.getCategorySharesMap());
    }

    @Test
    public void testGetTopThreeSuppliers() {
        assertArrayEquals(TopSuppliersData.allPayments, allPaymentsStatistic.getTopThreeSuppliers());
        assertArrayEquals(TopSuppliersData.january, januaryPaymentsStatistic.getTopThreeSuppliers());
        assertArrayEquals(TopSuppliersData.february, februaryPaymentsStatistic.getTopThreeSuppliers());
        assertArrayEquals(TopSuppliersData.notInDB, notInDBPaymentsStatistic.getTopThreeSuppliers());
    }
}