package org.example.statistic.data;


import org.example.entities.Payment;

import java.time.LocalDate;

import java.util.List;

public final class PaymentManagerData {
    public static List<Payment> paymentListByDate(LocalDate date, List<Payment> all) {
        return all.stream().filter(payment -> payment.getPaymentDateTime().toLocalDate().equals(date))
                .toList();
    }
}
