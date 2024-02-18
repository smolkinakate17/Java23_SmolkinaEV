package org.example.statistic.manager;

import org.example.entities.Payment;

import java.time.LocalDate;
import java.util.List;


public record PaymentManager(List<Payment> paymentList) {

    public List<Payment> getPaymentListByDate(LocalDate date) {
        return paymentList.stream()
                .filter(payment -> payment.getPaymentDateTime().toLocalDate().equals(date)).toList();
    }
}
