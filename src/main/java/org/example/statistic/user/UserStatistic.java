package org.example.statistic.user;

import jakarta.persistence.EntityManager;

import jakarta.persistence.Tuple;

import org.example.dtos.CategoryShareDTO;
import org.example.dtos.PaymentMethodShareDTO;
import org.example.entities.Category;

import org.example.entities.PaymentMethod;


import java.math.BigDecimal;
import java.util.*;

import java.util.stream.Collectors;

public final class UserStatistic {
    private final Period period;
    private final EntityManager entityManager;

    public UserStatistic(Period period, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.period = period;
    }

    public int getPaymentCount() {
        return entityManager.createQuery("select count(p.id) from Payment p where p.paymentDateTime between :fromDate and :toDate", Long.class).
                setParameter("fromDate", period.getFromDateTime()).setParameter("toDate", period.getToDateTime()).
                getSingleResult().intValue();
    }

    public BigDecimal getPaymentAmount() {
        BigDecimal result = entityManager.createQuery("select sum(p.amount) from Payment p where p.paymentDateTime between :fromDate and :toDate", BigDecimal.class).
                setParameter("fromDate", period.getFromDateTime()).setParameter("toDate", period.getToDateTime()).
                getSingleResult();
        if (result == null) {
            return BigDecimal.valueOf(0.0);
        }
        return result;

    }

    public Map<PaymentMethod, Share> getPaymentMethodSharesMap() {
        Map<PaymentMethod, Share> paymentMethodShareMap;

        paymentMethodShareMap = entityManager.createQuery(
                        "select p1.paymentMethod as method, sum(p1.amount) as amount, " +
                                "round (sum(p1.amount)/(select sum(p2.amount) from Payment p2 " +
                                "where p2.paymentDateTime between :fromDate and :toDate)*100,2) as percent " +
                                "from Payment p1 where p1.paymentDateTime between :fromDate and :toDate " +
                                "group by p1.paymentMethod"
                        , PaymentMethodShareDTO.class
                )
                .setParameter("fromDate", period.getFromDateTime()).setParameter("toDate", period.getToDateTime())
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                PaymentMethodShareDTO::getMethod,
                                PaymentMethodShareDTO::getShare
                        )
                );
        return paymentMethodShareMap;

    }

    public Map<Category, Share> getCategorySharesMap() {
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
                .setParameter("fromDate", period.getFromDateTime()).setParameter("toDate", period.getToDateTime())
                .getResultStream()
                .collect(
                        Collectors.toMap(
                                CategoryShareDTO::getCategory,
                                CategoryShareDTO::getShare
                        )
                );
        return categoryShareMap;
    }

    public String[] getTopThreeSuppliers() {
        List<String> result = entityManager.createQuery(
                        "select p.supplier from Payment p where p.paymentDateTime between :fromDate and :toDate " +
                                "group by p.supplier order by count(p.supplier) desc, p.supplier limit 3"
                        , String.class
                )
                .setParameter("fromDate", period.getFromDateTime()).setParameter("toDate", period.getToDateTime())
                .getResultList();
        return result.toArray(new String[0]);


    }
}
