package org.example.model.dtos;

import lombok.Getter;
import lombok.ToString;
import org.example.model.entities.PaymentMethod;
import org.example.model.statistic.user.Share;

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

    public PaymentMethodShareDTO() {
    }
}
