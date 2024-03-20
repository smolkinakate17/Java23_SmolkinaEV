package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Setter
    @Column(name = "title")
    private String title;
    @Setter
    @Column(name = "description")
    private String description;
    @Setter
    @Column(name = "color")
    private String color;

    public Category(String title, String description, String color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return getId() == category.getId() && getTitle().equals(category.getTitle()) && Objects.equals(getDescription(), category.getDescription()) && Objects.equals(getColor(), category.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getDescription(), getColor());
    }
}
