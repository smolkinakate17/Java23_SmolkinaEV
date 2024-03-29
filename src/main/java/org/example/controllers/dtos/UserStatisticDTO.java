package org.example.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.model.statistic.user.UserStatistic;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter

public class UserStatisticDTO implements Serializable {
    private LocalDate fromDate;
    private LocalDate toDate;
    private int allPayments;
    private double allAmount;
    private String[] topSuppliers;
    private List<PaymentMethodSharesDTO> paymentMethodShares;
    private List<CategorySharesDTO> categoryShares;


    public UserStatisticDTO(UserStatistic userStatistic, LocalDate from, LocalDate to) {
        this.fromDate = from;
        this.toDate = to;
        this.allPayments = userStatistic.getPaymentCount(LocalDateTime.of(from, LocalTime.MIN), LocalDateTime.of(to, LocalTime.MAX));
        this.allAmount = userStatistic.getPaymentAmount(LocalDateTime.of(from, LocalTime.MIN), LocalDateTime.of(to, LocalTime.MAX)).doubleValue();
        this.topSuppliers = userStatistic.getTopThreeSuppliers(LocalDateTime.of(from, LocalTime.MIN), LocalDateTime.of(to, LocalTime.MAX));
        this.paymentMethodShares = userStatistic.getPaymentMethodSharesMap(LocalDateTime.of(from, LocalTime.MIN), LocalDateTime.of(to, LocalTime.MAX))
                .entrySet().stream().
                map(entry -> new PaymentMethodSharesDTO(entry.getKey(), entry.getValue()))
                .toList();
        this.categoryShares = userStatistic.getCategorySharesMap(LocalDateTime.of(from, LocalTime.MIN), LocalDateTime.of(to, LocalTime.MAX))
                .entrySet().stream()
                .map(entry -> new CategorySharesDTO(entry.getKey(), entry.getValue()))
                .toList();
    }
}
