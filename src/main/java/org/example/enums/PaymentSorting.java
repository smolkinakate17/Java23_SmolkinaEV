package org.example.enums;

import lombok.Getter;

public enum PaymentSorting {
    DATETIME("paymentDateTime"),
    AMOUNT("amount");
    @Getter
    String title;

    PaymentSorting(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "PaymentSorting{" +
                "title='" + title + '\'' +
                '}';
    }
}
