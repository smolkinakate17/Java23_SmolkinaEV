package org.example.statistic.manager;


import org.example.statistic.data.PaymentData;
import org.example.statistic.data.PaymentManagerData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentManagerTest{
    private final PaymentManager paymentManager=new PaymentManager(PaymentData.paymentList);
    @Test
    public void testGetPaymentListByDate() {
        assertEquals(paymentManager.getPaymentListByDate(LocalDate.of(2024,1,15)), PaymentManagerData.januaryFifteen);
        assertEquals(PaymentManagerData.januaryFirst,paymentManager.getPaymentListByDate(LocalDate.of(2024,1,1)));
        assertEquals(PaymentManagerData.januaryTwenty,paymentManager.getPaymentListByDate(LocalDate.of(2024,1,20)));
        assertEquals(PaymentManagerData.marchSeventeen,paymentManager.getPaymentListByDate(LocalDate.of(2024,3,17)));
        assertEquals(PaymentManagerData.marchEighteen,paymentManager.getPaymentListByDate(LocalDate.of(2024,3,18)));
    }
}