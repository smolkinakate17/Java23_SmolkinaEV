package org.example.statistic.manager;

import jakarta.persistence.EntityManager;
import org.example.entities.Category;
import org.example.entities.PaymentMethod;
import org.example.exception.HasPaymentException;
import org.example.exception.ValueAlreadyExistException;
import org.example.exception.ValueNotExistException;

import java.util.Optional;


public class Dictionary<T> {

    private final EntityManager entityManager;
    private final PaymentManager paymentManager;

    public Dictionary(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.paymentManager = new PaymentManager(entityManager);
    }

    private Optional<T> find(T value) {

        if (value instanceof Category) {
            Category c = entityManager.find(Category.class, ((Category) value).getId());
            if (c == null) {
                return Optional.empty();
            }
            return (Optional<T>) Optional.of(c);
        } else if (value instanceof PaymentMethod) {
            PaymentMethod m = entityManager.find(PaymentMethod.class, ((PaymentMethod) value).getId());
            if (m == null) {
                return Optional.empty();
            }
            return (Optional<T>) Optional.of(m);
        } else throw new ClassCastException("Only for PaymentMethod, Category");
    }


    public void add(T value) throws ValueAlreadyExistException {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            Optional<T> find = find(value);
            if (find.isPresent()) {
                transaction.rollback();
                throw new ValueAlreadyExistException(value);
            }
            entityManager.persist(value);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    public void update(T value) throws ValueNotExistException {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        Optional<T> find = find(value);
        if (find.isEmpty()) {
            transaction.rollback();
            throw new ValueNotExistException(value);
        }
        entityManager.merge(value);
        transaction.commit();
    }

    public void delete(T value) throws ValueNotExistException {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        Optional<T> find = find(value);
        if (find.isEmpty()) {
            transaction.rollback();
            throw new ValueNotExistException(value);
        }
        if (value instanceof Category) {
            if (paymentManager.getPaymentListByCategory((Category) value).size() != 0) {
                transaction.rollback();
                throw new HasPaymentException(value);
            }
        }
        if (value instanceof PaymentMethod) {
            if (paymentManager.getPaymentListByPaymentMethod((PaymentMethod) value).size() != 0) {
                transaction.rollback();
                throw new HasPaymentException(value);
            }
        }
        entityManager.remove(value);
        transaction.commit();

    }
}
