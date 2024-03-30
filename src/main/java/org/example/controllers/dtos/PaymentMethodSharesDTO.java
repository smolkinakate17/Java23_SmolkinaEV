package org.example.controllers.dtos;


import lombok.Getter;
import org.example.model.entities.PaymentMethod;
import org.example.model.statistic.user.Share;

import java.io.Serializable;

@Getter
public class PaymentMethodSharesDTO implements Serializable {
    private String method;
    private double amount;
    private double percent;


    public PaymentMethodSharesDTO(PaymentMethod method, Share share) {
        this.method = method.getTitle();
        this.amount = share.getAmount();
        this.percent = share.getPercent();
    }
}
