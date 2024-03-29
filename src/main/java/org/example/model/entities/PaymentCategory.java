package org.example.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "payment_category")
public class PaymentCategory {
    static class PaymentCategoryPK implements Serializable {
        @Getter
        @Setter
        @ManyToOne
        @JoinColumn(name = "payment_id")
        private Payment payment;
        @Getter
        @Setter
        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PaymentCategoryPK that)) return false;
            return getPayment().equals(that.getPayment()) && getCategory().equals(that.getCategory());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getPayment(), getCategory());
        }

        @Override
        public String toString() {
            return "PaymentCategoryPK{" +
                    "payment=" + payment.getId() +
                    ", category=" + category.getId() +
                    '}';
        }
    }

    @EmbeddedId
    private PaymentCategoryPK pk = new PaymentCategoryPK();

    public Category getCategory() {
        return pk.category;
    }

    public Payment getPayment() {
        return pk.payment;
    }

    public PaymentCategory(@NonNull Payment payment, @NonNull Category category) {
        pk.category = category;
        pk.payment = payment;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentCategory that)) return false;
        return getPk().equals(that.getPk());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPk());
    }

    @Override
    public String toString() {
        return "PaymentCategory{" +
                "pk=" + pk +
                '}';
    }
}
