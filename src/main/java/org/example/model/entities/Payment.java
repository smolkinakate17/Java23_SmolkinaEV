package org.example.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "payment")

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private long id;
    @Setter
    @Column(name = "payment_date_time")
    private LocalDateTime datetime;
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
    @OneToMany(mappedBy = "pk.payment", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<PaymentCategory> paymentCategories = new ArrayList<>();

    public Optional<PaymentCategory> findCategory(Category category) {
        return paymentCategories.stream().filter(paymentCategory -> paymentCategory.getCategory().equals(category))
                .findFirst();
    }

    public PaymentCategory addCategory(Category category) {
        Optional<PaymentCategory> find = findCategory(category);
        if (find.isPresent()) {
            return find.get();
        }
        PaymentCategory paymentCategory = new PaymentCategory(this, category);
        paymentCategories.add(paymentCategory);
        return paymentCategory;
    }

    public Payment(LocalDateTime paymentDateTime, String supplier, BigDecimal amount) {
        this.datetime = paymentDateTime;
        this.supplier = supplier;
        this.amount = amount;
    }

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

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", paymentDateTime=" + datetime +
                ", supplier='" + supplier + '\'' +
                ", amount=" + amount +
                ", paymentMethod=" + paymentMethod +
                ", paymentCategories=" + paymentCategories +
                '}';
    }
}
