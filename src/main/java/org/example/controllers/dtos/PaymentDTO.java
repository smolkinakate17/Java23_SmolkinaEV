package org.example.controllers.dtos;

import lombok.Data;
import org.example.model.entities.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaymentDTO {
    private long id;
    private LocalDateTime dateTime;
    private BigDecimal amount;

    private PaymentMethod method;
    private String supplier;

    private List<Long> categoryList=new ArrayList<>();


}
