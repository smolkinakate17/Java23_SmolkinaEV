package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@ToString

public class Payment {
    private long id;
    private LocalDateTime paymentDateTime;
    private String supplier;
    private double amount;
    private PaymentMethod paymentMethod;
    private List<Category> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return getId() == payment.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
