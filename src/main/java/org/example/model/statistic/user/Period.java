package org.example.model.statistic.user;


import lombok.Getter;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter

public class Period {
    private final LocalDateTime fromDateTime;
    private final LocalDateTime toDateTime;

    public Period(LocalDate fromDate, LocalDate toDate) {
        this.fromDateTime = LocalDateTime.of(fromDate, LocalTime.MIN);
        this.toDateTime = LocalDateTime.of(toDate, LocalTime.MAX);
    }

    public boolean validateDate(LocalDateTime dateTime) {
        return !(dateTime.isAfter(toDateTime) || dateTime.isBefore(fromDateTime));
    }
}
