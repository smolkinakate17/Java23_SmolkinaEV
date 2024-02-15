package org.example.statistic.user;

import lombok.AllArgsConstructor;


import java.time.LocalDate;


@AllArgsConstructor
public class Period {
    private LocalDate fromDate;
    private LocalDate toDate;

    public boolean validateDate(LocalDate date){
        return !(date.isAfter(toDate)||date.isBefore(fromDate));
    }
}
