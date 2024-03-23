package org.example.dtos;

import lombok.Getter;
import lombok.ToString;
import org.example.entities.PaymentMethod;
import org.example.statistic.user.Share;

import java.math.BigDecimal;

@Getter
@ToString
public class PaymentMethodShareDTO {
    private PaymentMethod method;
    private Share share;

    public PaymentMethodShareDTO(PaymentMethod method, BigDecimal amount, BigDecimal percent) {
        this.method = method;
        share = new Share(amount.doubleValue(), percent.doubleValue());
    }
}
