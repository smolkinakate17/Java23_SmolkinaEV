package org.example.statistic.user;

import org.example.statistic.data.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserStatisticTest {
    @Test
    public void testGetPaymentCount() {
        assertEquals(20, UserStatisticData.allPayments.getPaymentCount());
        assertEquals(8, UserStatisticData.january.getPaymentCount());
        assertEquals(3, UserStatisticData.february.getPaymentCount());
        assertEquals(9, UserStatisticData.march.getPaymentCount());
        assertEquals(11, UserStatisticData.januaryAndFebruary.getPaymentCount());
        assertEquals(12, UserStatisticData.februaryAndMarch.getPaymentCount());
    }
    @Test
    public void testGetPaymentAmount() {
        assertEquals(30284.78, UserStatisticData.allPayments.getPaymentAmount());
        assertEquals(10146.02, UserStatisticData.january.getPaymentAmount());
        assertEquals(9566.67, UserStatisticData.february.getPaymentAmount());
        assertEquals(10572.09, UserStatisticData.march.getPaymentAmount());
        assertEquals(19712.69, UserStatisticData.januaryAndFebruary.getPaymentAmount());
        assertEquals(20138.76, UserStatisticData.februaryAndMarch.getPaymentAmount());
    }

    @Test
    public void testGetPaymentMethodSharesMap() {
        assertEquals(PaymentMethodShareMapData.allPayments(),
                UserStatisticData.allPayments.getPaymentMethodSharesMap());
        assertEquals(PaymentMethodShareMapData.january(),
                UserStatisticData.january.getPaymentMethodSharesMap());
        assertEquals(PaymentMethodShareMapData.february(),
                UserStatisticData.february.getPaymentMethodSharesMap());
        assertEquals(PaymentMethodShareMapData.march(),
                UserStatisticData.march.getPaymentMethodSharesMap());
        assertEquals(PaymentMethodShareMapData.januaryAndFebruary(),
                UserStatisticData.januaryAndFebruary.getPaymentMethodSharesMap());
        assertEquals(PaymentMethodShareMapData.februaryAndMarch(),
                UserStatisticData.februaryAndMarch.getPaymentMethodSharesMap());
    }

    @Test
    public void testGetCategorySharesMap() {
        assertEquals(CategoryShareMapData.allPayments(), UserStatisticData.allPayments.getCategorySharesMap());
        assertEquals(CategoryShareMapData.january(), UserStatisticData.january.getCategorySharesMap());
        assertEquals(CategoryShareMapData.february(), UserStatisticData.february.getCategorySharesMap());
        assertEquals(CategoryShareMapData.march(), UserStatisticData.march.getCategorySharesMap());
        assertEquals(CategoryShareMapData.januaryAndFebruary(), UserStatisticData.januaryAndFebruary.getCategorySharesMap());
        assertEquals(CategoryShareMapData.februaryAndMarch(), UserStatisticData.februaryAndMarch.getCategorySharesMap());

    }

    @Test
    public void testGetTopThreeSuppliers() {
        assertArrayEquals(TopSuppliersData.allPayments, UserStatisticData.allPayments.getTopThreeSuppliers());
        assertArrayEquals(TopSuppliersData.january,UserStatisticData.january.getTopThreeSuppliers());
        assertArrayEquals(TopSuppliersData.february,UserStatisticData.february.getTopThreeSuppliers());
        assertArrayEquals(TopSuppliersData.march,UserStatisticData.march.getTopThreeSuppliers());
        assertArrayEquals(TopSuppliersData.januaryAndFebruary,UserStatisticData.januaryAndFebruary.getTopThreeSuppliers());
        assertArrayEquals(TopSuppliersData.februaryAndMarch,UserStatisticData.februaryAndMarch.getTopThreeSuppliers());
    }
}