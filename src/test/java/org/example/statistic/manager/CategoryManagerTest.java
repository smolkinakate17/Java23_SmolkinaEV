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

class CategoryManagerTest {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CategoryManager categoryManager;
    private List<Category> allCategoryList;

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

        categoryManager = new CategoryManager(entityManager);
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
        Category c = new Category("test", "test", "test");
        allCategoryList.add(c);
        c = categoryManager.add(c);
        List<Category> afterAddCategory = entityManager.createQuery("from Category c", Category.class).getResultList();
        assertEquals(allCategoryList.size(), afterAddCategory.size());
        assertTrue(afterAddCategory.contains(c));

        Category exist = afterAddCategory.get(1);
        Assertions.assertThrows(ValueAlreadyExistException.class, () ->
                categoryManager.add(exist));
    }

    @Test
    void update() {
        Category c = allCategoryList.get(allCategoryList.size() - 1);
        c.setTitle("newTestTitle");
        categoryManager.update(c);
        List<Category> afterUpdateCategory = entityManager.createQuery("from Category c", Category.class).getResultList();
        assertTrue(afterUpdateCategory.contains(c));

        Category notExist = new Category();
        Assertions.assertThrows(ValueNotExistException.class, () ->
                categoryManager.delete(notExist));
    }

    @Test
    void delete() {
        Category c = new Category("test", "test", "test");
        categoryManager.add(c);
        allCategoryList = entityManager.createQuery(
                        "from Category c"
                        , Category.class
                )
                .getResultList();
        allCategoryList.remove(c);
        categoryManager.delete(c);
        List<Category> afterDeleteCategory = entityManager.createQuery("from Category c", Category.class).getResultList();
        assertFalse(afterDeleteCategory.contains(c));

        Category havePayments = categoryManager.find(1).get();
        Assertions.assertThrows(ConstraintViolationException.class, () ->
                categoryManager.delete(havePayments));
    }
}