package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "payment")

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    @Column(name = "payment_date_time")
    private LocalDateTime paymentDateTime;
    @Setter
    @Column(name = "supplier")
    private String supplier;
    @Setter
    @Column(name = "amount", columnDefinition = "numeric(10,2)")
    private BigDecimal amount;
    @Setter
    @ManyToOne
    @JoinColumn(name = "payment_method")
    private PaymentMethod paymentMethod;
    @OneToMany(mappedBy = "pk.payment")
    private List<PaymentCategory> paymentCategories;

    public Payment(LocalDateTime paymentDateTime, String supplier, BigDecimal amount) {
        this.paymentDateTime = paymentDateTime;
        this.supplier = supplier;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment payment)) return false;
        return getId() == payment.getId() && Objects.equals(getPaymentDateTime(), payment.getPaymentDateTime()) && Objects.equals(getSupplier(), payment.getSupplier()) && getAmount().equals(payment.getAmount()) && Objects.equals(getPaymentMethod(), payment.getPaymentMethod()) && Objects.equals(getPaymentCategories(), payment.getPaymentCategories());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPaymentDateTime(), getSupplier(), getAmount(), getPaymentMethod(), getPaymentCategories());
    }
}
