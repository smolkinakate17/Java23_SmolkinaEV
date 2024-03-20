package org.example.statistic.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
@ToString

public class Share {
    private double amount;
    private double percent;

    public Share(double amount, double percent) {
        this.amount = amount;
        this.percent = percent;
    }

    public Share(int amountInKopecks, int allAmountInKopecks) {
        this.amount = amountInKopecks / 100.0;
        long roundValue = Math.round(((double) amountInKopecks / (double) allAmountInKopecks) * 10000);
        double fromLong = BigDecimal.valueOf(roundValue).doubleValue();
        this.percent = fromLong / 100.0;

    }
}
