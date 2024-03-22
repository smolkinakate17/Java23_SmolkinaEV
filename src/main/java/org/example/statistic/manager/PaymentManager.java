package org.example.statistic.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.entities.PaymentMethod;
import org.example.enums.PaymentSorting;
import org.example.exception.ValueNotExistException;

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

    public List<Payment> getPaymentListByCategory(Category category) {
        return entityManager.createQuery(
                        "select pc.pk.payment from PaymentCategory  pc where pc.pk.category=:category", Payment.class
                )
                .setParameter("category", category).getResultList();
    }

    public List<Payment> getPaymentListByPaymentMethod(PaymentMethod method) {
        return entityManager.createQuery(
                        "select p from Payment p where p.paymentMethod=:mehod", Payment.class
                )
                .setParameter("mehod", method).getResultList();
    }

    public Optional<Payment> find(Payment payment) {
        Payment p = entityManager.find(Payment.class, payment.getId());
        if (p == null) {
            return Optional.empty();
        }
        return Optional.of(p);
    }

    public void add(Payment payment) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(payment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }

    }

    public void update(Payment payment) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(payment);
        transaction.commit();
    }

    public void delete(Payment payment) {
        var transaction = entityManager.getTransaction();
        transaction.begin();
        Optional<Payment> find = find(payment);
        if (find.isEmpty()) {
            transaction.rollback();
            throw new ValueNotExistException(payment);
        }
        entityManager.remove(payment);
        transaction.commit();
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
