package org.example.dtos;

import lombok.Getter;
import lombok.ToString;
import org.example.entities.Category;
import org.example.statistic.user.Share;

import java.math.BigDecimal;

@Getter
@ToString
public class CategoryShareDTO {
    private Category category;
    private Share share;

    public CategoryShareDTO(Category category, BigDecimal amount, BigDecimal percent) {
        this.category = category;
        share = new Share(amount.doubleValue(), percent.doubleValue());
    }

}
