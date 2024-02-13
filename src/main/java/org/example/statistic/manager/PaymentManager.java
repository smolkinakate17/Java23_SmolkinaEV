package org.example.statistic.manager;

import lombok.Getter;
import org.example.entities.Payment;

import java.time.LocalDate;
import java.util.List;
@Getter
public class PaymentManager {
    private List<Payment> paymentList;

    public PaymentManager(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Payment> getPaymentListByDate(LocalDate date){
        return paymentList.stream()
                .filter(payment -> payment.getPaymentDateTime().toLocalDate().equals(date)).toList();
    }
}
