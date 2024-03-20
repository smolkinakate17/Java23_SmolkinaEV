package org.example.statistic.data;

import org.example.statistic.user.Period;

import java.time.LocalDate;

public class PeriodData {
    public static final Period allPayments = new Period(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31));
    public static final Period january = new Period(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 31));
    public static final Period february = new Period(LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 28));
    public static final Period march = new Period(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31));
    public static final Period april = new Period(LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 30));
    public static final Period may = new Period(LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 31));
    public static final Period notInDB = new Period(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 2, 1));
}
