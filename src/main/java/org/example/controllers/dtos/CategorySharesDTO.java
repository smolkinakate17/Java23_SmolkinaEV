package org.example.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.model.entities.Category;
import org.example.model.statistic.user.Share;

import java.io.Serializable;

@Getter
@Setter
public class CategorySharesDTO implements Serializable {
    private String title;
    private String color;
    private double amount;
    private double percent;

    public CategorySharesDTO(Category category, Share share) {
        this.title = category.getTitle();
        this.color = category.getColor();
        this.amount = share.getAmount();
        this.percent = share.getPercent();
    }
}
