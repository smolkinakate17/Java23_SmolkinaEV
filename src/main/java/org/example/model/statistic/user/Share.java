package org.example.model.statistic.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    public Share(@JsonProperty("amount")double amount, @JsonProperty("percent")double percent) {
        this.amount = amount;
        this.percent = percent;
    }
    public Share(BigDecimal amount,BigDecimal percent){
        this.amount=amount.doubleValue();
        this.percent=percent.doubleValue();
    }

    public Share(int amountInKopecks, int allAmountInKopecks) {
        this.amount = amountInKopecks / 100.0;
        long roundValue = Math.round(((double) amountInKopecks / (double) allAmountInKopecks) * 10000);
        double fromLong = BigDecimal.valueOf(roundValue).doubleValue();
        this.percent = fromLong / 100.0;

    }
}
