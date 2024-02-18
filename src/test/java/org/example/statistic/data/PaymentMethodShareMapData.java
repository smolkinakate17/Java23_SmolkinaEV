package org.example.statistic.data;

import org.example.entities.Payment;
import org.example.entities.PaymentMethod;
import org.example.statistic.user.Share;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PaymentMethodShareMapData {
    private static Share getShareByPaymentMethod(PaymentMethod method, List<Payment> paymentList, double allAmount) {
        int amount = paymentList.stream().filter(payment -> payment.getPaymentMethod().equals(method))
                .mapToDouble(payment-> payment.getAmount()*100).mapToLong(Math::round).mapToInt(Math::toIntExact).sum();
        return new Share(amount, (int)allAmount*100);
    }

    public static Map<PaymentMethod, Share> allPayments() {
        Map<PaymentMethod, Share> map = new HashMap<>();
        map.put(PaymentMethodData.cash, getShareByPaymentMethod(PaymentMethodData.cash,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(PaymentMethodData.card, getShareByPaymentMethod(PaymentMethodData.card,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(PaymentMethodData.creditCard, getShareByPaymentMethod(PaymentMethodData.creditCard,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(PaymentMethodData.bankDeposit, getShareByPaymentMethod(PaymentMethodData.bankDeposit,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        return map;

    }

    public static Map<PaymentMethod, Share> january() {
        Map<PaymentMethod, Share> map = new HashMap<>();
        map.put(PaymentMethodData.cash, getShareByPaymentMethod(PaymentMethodData.cash,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        map.put(PaymentMethodData.card, getShareByPaymentMethod(PaymentMethodData.card,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        map.put(PaymentMethodData.bankDeposit, getShareByPaymentMethod(PaymentMethodData.bankDeposit,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        return map;
    }

    public static Map<PaymentMethod, Share> february() {
        Map<PaymentMethod, Share> map = new HashMap<>();
        map.put(PaymentMethodData.card, getShareByPaymentMethod(PaymentMethodData.card,
                UserStatisticData.february.getPaymentList(), UserStatisticData.february.getPaymentAmount()));
        map.put(PaymentMethodData.creditCard, getShareByPaymentMethod(PaymentMethodData.creditCard,
                UserStatisticData.february.getPaymentList(), UserStatisticData.february.getPaymentAmount()));
        return map;
    }

    public static Map<PaymentMethod, Share> march() {
        Map<PaymentMethod, Share> map = new HashMap<>();
        map.put(PaymentMethodData.card, getShareByPaymentMethod(PaymentMethodData.card,
                UserStatisticData.march.getPaymentList(), UserStatisticData.march.getPaymentAmount()));
        map.put(PaymentMethodData.cash, getShareByPaymentMethod(PaymentMethodData.cash,
                UserStatisticData.march.getPaymentList(), UserStatisticData.march.getPaymentAmount()));
        return map;
    }

    public static Map<PaymentMethod, Share> januaryAndFebruary() {
        Map<PaymentMethod, Share> map = new HashMap<>();
        map.put(PaymentMethodData.cash, getShareByPaymentMethod(PaymentMethodData.cash,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(PaymentMethodData.card, getShareByPaymentMethod(PaymentMethodData.card,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(PaymentMethodData.bankDeposit, getShareByPaymentMethod(PaymentMethodData.bankDeposit,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(PaymentMethodData.creditCard, getShareByPaymentMethod(PaymentMethodData.creditCard,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        return map;
    }

    public static Map<PaymentMethod, Share> februaryAndMarch() {
        Map<PaymentMethod, Share> map = new HashMap<>();
        map.put(PaymentMethodData.cash, getShareByPaymentMethod(PaymentMethodData.cash,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        map.put(PaymentMethodData.card, getShareByPaymentMethod(PaymentMethodData.card,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        map.put(PaymentMethodData.creditCard, getShareByPaymentMethod(PaymentMethodData.creditCard,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        return map;
    }

}
