package org.example.statistic.manager;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.entities.PaymentCategory;
import org.example.entities.PaymentMethod;
import org.example.enums.PaymentSorting;
import org.example.exceptions.ValueNotExistException;
import org.example.statistic.SqlScript;
import org.example.statistic.data.PaymentManagerData;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        //SqlScript.runFromFile(entityManagerFactory, "DeleteAll.sql");
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

        Payment paymentWithCategory = new Payment(LocalDateTime.of(2024, 1, 1, 2, 2), "newTest", BigDecimal.TWO);
        Category categoryWithPayment = entityManager.createQuery(
                "select c from Category c where c.id=1", Category.class
        ).getSingleResult();
        paymentWithCategory.addCategory(categoryWithPayment);
        paymentManager.add(paymentWithCategory);
        Optional<Payment> addedPayment = paymentManager.find(paymentWithCategory);
        assertTrue(addedPayment.isPresent());
        assertEquals(paymentWithCategory.getPaymentCategories(), addedPayment.get().getPaymentCategories());
        PaymentCategory addedPaymentCategory = entityManager.createQuery(
                        "select pc from PaymentCategory pc where pc.pk.category=:category and pc.pk.payment=:payment"
                        , PaymentCategory.class
                ).setParameter("category", categoryWithPayment).setParameter("payment", paymentWithCategory)
                .getSingleResult();
        PaymentCategory beforeAdd = paymentWithCategory.getPaymentCategories().get(0);
        assertEquals(beforeAdd, addedPaymentCategory);

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
        Payment payment = allPayments.stream().filter(p -> p.getPaymentCategories().size() > 0).findFirst().get();
        List<PaymentCategory> beforeDeletePaymentCategories = payment.getPaymentCategories();
        paymentManager.delete(payment);
        allPayments.remove(payment);
        List<Payment> afterDelete = entityManager.createQuery("from Payment p", Payment.class).getResultList();
        List<PaymentCategory> afterDeletePaymentCategories = entityManager.createQuery("from PaymentCategory pc", PaymentCategory.class).getResultList();

        assertFalse(afterDelete.contains(payment));
        for (PaymentCategory paymentCategory : beforeDeletePaymentCategories) {
            assertFalse(afterDeletePaymentCategories.contains(paymentCategory));
        }

        Payment bad = new Payment();
        Assertions.assertThrows(ValueNotExistException.class, () ->
                paymentManager.delete(bad));


    }

    @Test
    public void testGetPaymentListSortedPaginal() {
        List<Payment> streamSort = allPayments.stream().sorted(Comparator.comparing(Payment::getAmount).reversed()).toList();
        List<Payment> mySort = paymentManager.getPaymentListSortedPaginal(PaymentSorting.AMOUNT, true, 0, 10);
        testSort(streamSort, mySort, 0, 10);
        mySort = paymentManager.getPaymentListSortedPaginal(PaymentSorting.AMOUNT, true, 1, 10);
        testSort(streamSort, mySort, 1, 10);

        streamSort = allPayments.stream().sorted(Comparator.comparing(Payment::getAmount)).toList();
        mySort = paymentManager.getPaymentListSortedPaginal(PaymentSorting.AMOUNT, false, 0, 20);
        testSort(streamSort, mySort, 0, 20);
        mySort = paymentManager.getPaymentListSortedPaginal(PaymentSorting.AMOUNT, false, 1, 20);
        testSort(streamSort, mySort, 1, 20);

        streamSort = allPayments.stream().sorted(Comparator.comparing(Payment::getPaymentDateTime).reversed()).toList();
        mySort = paymentManager.getPaymentListSortedPaginal(PaymentSorting.DATETIME, true, 0, 15);
        testSort(streamSort, mySort, 0, 15);
        mySort = paymentManager.getPaymentListSortedPaginal(PaymentSorting.DATETIME, true, 1, 15);
        testSort(streamSort, mySort, 1, 15);

        streamSort = allPayments.stream().sorted(Comparator.comparing(Payment::getPaymentDateTime)).toList();
        mySort = paymentManager.getPaymentListSortedPaginal(PaymentSorting.DATETIME, false, 0, 10);
        testSort(streamSort, mySort, 0, 10);
        mySort = paymentManager.getPaymentListSortedPaginal(PaymentSorting.DATETIME, false, 1, 10);
        testSort(streamSort, mySort, 1, 10);
    }

    private void testSort(List<Payment> streamSort, List<Payment> mySort, int page, int pageSize) {
        for (int i = 0; i < mySort.size(); i++) {
            int index = page * pageSize + i;
            assertEquals(mySort.get(i), streamSort.get(index));
        }
    }

}