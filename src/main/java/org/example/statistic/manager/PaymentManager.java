package org.example.statistic.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.entities.Payment;
import org.example.enums.PaymentSorting;
import org.example.exceptions.ValueNotExistException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PaymentManager {
    private final EntityManager entityManager;

    public List<Payment> getPaymentListByDate(LocalDate date) {
        return entityManager.createQuery(
                        "select p from Payment p where p.paymentDateTime between : fromDateTime and :toDateTime", Payment.class
                )
                .setParameter("fromDateTime", LocalDateTime.of(date, LocalTime.MIN)).setParameter("toDateTime", LocalDateTime.of(date, LocalTime.MAX))
                .getResultList();
    }

    public Optional<Payment> find(Payment payment) {
        Payment p = entityManager.find(Payment.class, payment.getId());
        if (p == null) {
            return Optional.empty();
        }
        return Optional.of(p);
    }
    public Optional<Payment> find(long id){
        Payment p = entityManager.find(Payment.class, id);
        if (p == null) {
            return Optional.empty();
        }
        return Optional.of(p);
    }

    public Payment add(Payment payment) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(payment);
            transaction.commit();
            return payment;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }

    }

    public Payment update(Payment payment) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(payment);
        transaction.commit();
        return payment;
    }

    public Payment delete(Payment payment) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        Optional<Payment> find = find(payment);
        if (find.isEmpty()) {
            transaction.rollback();
            throw new ValueNotExistException(payment);
        }
        entityManager.remove(payment);
        transaction.commit();
        return payment;
    }
    public Payment delete(long id){
        var transaction = entityManager.getTransaction();
        transaction.begin();
        Optional<Payment> find = find(id);
        if (find.isEmpty()) {
            transaction.rollback();
            throw new ValueNotExistException(id);
        }
        entityManager.remove(find.get());
        transaction.commit();
        return find.get();
    }


    public List<Payment> getPaymentListSortedPaginal(PaymentSorting sorting, boolean desc, int page, int pageSize) {
        CriteriaQuery<Payment> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Payment.class);
        Root<Payment> root = criteriaQuery.from(Payment.class);
        var sortBy = root.get(sorting.getTitle());
        var order = desc
                ? entityManager.getCriteriaBuilder().desc(sortBy)
                : entityManager.getCriteriaBuilder().asc(sortBy);
        criteriaQuery.orderBy(order);
        return entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(page * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
