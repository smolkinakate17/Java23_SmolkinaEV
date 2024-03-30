package org.example.model.enums;

import lombok.Getter;

public enum PaymentSorting {
    DATETIME("datetime"),
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
