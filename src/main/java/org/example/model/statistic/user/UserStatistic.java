package org.example.model.statistic.user;

import jakarta.persistence.EntityManager;

import org.example.model.dtos.CategoryShareDTO;
import org.example.model.dtos.PaymentMethodShareDTO;
import org.example.model.entities.Category;

import org.example.model.entities.PaymentMethod;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import java.util.stream.Collectors;

@Service
public final class UserStatistic {
    private final EntityManager entityManager;

    public UserStatistic(EntityManager entityManager) {
        this.entityManager = entityManager;

    }

    public int getPaymentCount(LocalDateTime from,LocalDateTime to) {
        return entityManager.createQuery("select count(p.id) from Payment p where p.paymentDateTime between :fromDate and :toDate", Long.class).
                setParameter("fromDate", from).setParameter("toDate", to).
                getSingleResult().intValue();
    }

    public BigDecimal getPaymentAmount(LocalDateTime from,LocalDateTime to) {
        BigDecimal result = entityManager.createQuery("select sum(p.amount) from Payment p where p.paymentDateTime between :fromDate and :toDate", BigDecimal.class).
                setParameter("fromDate", from).setParameter("toDate", to).
                getSingleResult();
        if (result == null) {
            return BigDecimal.valueOf(0.0);
        }
        return result;

    }

    public Map<PaymentMethod, Share> getPaymentMethodSharesMap(LocalDateTime from,LocalDateTime to) {
        Map<PaymentMethod, Share> paymentMethodShareMap;

        paymentMethodShareMap = entityManager.createQuery(
                        "select p1.paymentMethod as method, sum(p1.amount) as amount, " +
                                "round (sum(p1.amount)/(select sum(p2.amount) from Payment p2 " +
                                "where p2.paymentDateTime between :fromDate and :toDate)*100,2) as percent " +
                                "from Payment p1 where p1.paymentDateTime between :fromDate and :toDate " +
                                "group by p1.paymentMethod"
                        , PaymentMethodShareDTO.class
                )
                .setParameter("fromDate", from).setParameter("toDate", to)
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                PaymentMethodShareDTO::getMethod,
                                PaymentMethodShareDTO::getShare
                        )
                );
        return paymentMethodShareMap;

    }

    public Map<Category, Share> getCategorySharesMap(LocalDateTime from,LocalDateTime to) {
        Map<Category, Share> categoryShareMap = entityManager.createQuery(
                        "select pc.pk.category as category, sum(pc.pk.payment.amount) as amount," +
                                "round (sum(pc.pk.payment.amount)/(" +
                                "select sum(sub_p.amount) from Payment sub_p where " +
                                "sub_p.paymentDateTime between :fromDate and :toDate)" +
                                "*100, 2) as percent " +
                                "from PaymentCategory pc join Payment p on pc.pk.payment.id=p.id " +
                                "join Category c on pc.pk.category.id=c.id " +
                                "where p.paymentDateTime between :fromDate and :toDate " +
                                "group by category",
                        CategoryShareDTO.class
                )
                .setParameter("fromDate", from).setParameter("toDate", to)
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                CategoryShareDTO::getCategory,
                                CategoryShareDTO::getShare
                        )
                );
        return categoryShareMap;
    }

    public String[] getTopThreeSuppliers(LocalDateTime from,LocalDateTime to) {
        List<String> result = entityManager.createQuery(
                        "select p.supplier from Payment p where p.paymentDateTime between :fromDate and :toDate " +
                                "group by p.supplier order by count(p.supplier) desc, p.supplier limit 3"
                        , String.class
                )
                .setParameter("fromDate", from).setParameter("toDate", to)
                .getResultList();
        return result.toArray(new String[0]);


    }


}
