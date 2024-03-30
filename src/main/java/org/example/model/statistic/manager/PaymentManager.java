package org.example.model.statistic.manager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.example.model.entities.Payment;
import org.example.model.enums.PaymentSorting;
import org.example.model.exceptions.ValueNotExistException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentManager {
    private final EntityManager entityManager;

    public List<Payment> getPaymentListByDate(LocalDate from,LocalDate to) {
        return entityManager.createQuery(
                        "select p from Payment p where p.datetime between : fromDateTime and :toDateTime", Payment.class
                )
                .setParameter("fromDateTime", LocalDateTime.of(from, LocalTime.MIN)).setParameter("toDateTime", LocalDateTime.of(to, LocalTime.MAX))
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
    public List<Payment> findAll(){
        return entityManager.createQuery("select p from Payment p", Payment.class).getResultList();
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
        System.out.println("UPDATE BEGIN");
        var transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(payment);
                /*.createQuery(
                "update Payment p set p.paymentMethod=:method, p.supplier=:supplier, p.paymentCategories=:categoties, p.amount=:amount, p.datetime=:datetime where p.id=:id"
        ).setParameter("method",payment.getPaymentMethod())
                .setParameter("supplier",payment.getSupplier())
                .setParameter("categoties",payment.getPaymentCategories())
                .setParameter("amount",payment.getAmount())
                .setParameter("id",payment.getId());*/
        transaction.commit();
        System.out.println("UPDATE END");
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


    public List<Payment> getPaymentListByDateSortedPaginal(PaymentSorting sorting, boolean desc, int page, int pageSize,LocalDate from, LocalDate to) {
        CriteriaBuilder criteriaBuilder=entityManager.getCriteriaBuilder();
        CriteriaQuery<Payment> criteriaQuery = criteriaBuilder.createQuery(Payment.class);
        Root<Payment> root = criteriaQuery.from(Payment.class);
        var sortBy = root.get(sorting.getTitle());
        var order = desc
                ? entityManager.getCriteriaBuilder().desc(sortBy)
                : entityManager.getCriteriaBuilder().asc(sortBy);
        criteriaQuery.where(criteriaBuilder.between(root.get("datetime"),LocalDateTime.of(from,LocalTime.MIN),LocalDateTime.of(to,LocalTime.MAX)));
        criteriaQuery.orderBy(order);
        return entityManager
                .createQuery(criteriaQuery)
                .setFirstResult(page * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
