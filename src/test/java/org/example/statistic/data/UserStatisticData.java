package org.example.statistic.data;

import org.example.statistic.user.Period;
import org.example.statistic.user.UserStatistic;

import java.time.LocalDate;

public final class UserStatisticData {
    public static final UserStatistic allPayments=new UserStatistic(new Period(LocalDate.of(2024,1,1),LocalDate.of(2024,3,20)),
            PaymentData.paymentList);
    public static final UserStatistic january=new UserStatistic(new Period(LocalDate.of(2024,1,1),LocalDate.of(2024,1,31)),
            PaymentData.paymentList);
    public static final UserStatistic february=new UserStatistic(new Period(LocalDate.of(2024,2,1),LocalDate.of(2024,2,28)),
            PaymentData.paymentList);
    public static final UserStatistic march=new UserStatistic(new Period(LocalDate.of(2024,3,1),LocalDate.of(2024,3,31)),
            PaymentData.paymentList);
    public static final UserStatistic januaryAndFebruary=new UserStatistic(new Period(LocalDate.of(2024,1,1),LocalDate.of(2024,2,28)),
            PaymentData.paymentList);
    public static final UserStatistic februaryAndMarch=new UserStatistic(new Period(LocalDate.of(2024,2,1),LocalDate.of(2024,3,31)),
            PaymentData.paymentList);
}
