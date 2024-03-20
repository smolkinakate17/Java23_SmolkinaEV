package org.example.statistic.data;

import org.example.entities.Payment;
import org.example.entities.PaymentMethod;
import org.example.statistic.user.Share;
import org.example.statistic.user.UserStatistic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PaymentMethodShareMapData {
    private static Share getShareByPaymentMethod(PaymentMethod method, List<Payment> paymentList, double allAmount) {
        int amount = paymentList.stream().filter(payment -> payment.getPaymentMethod().equals(method))
                .mapToDouble(payment -> payment.getAmount().doubleValue() * 100).mapToLong(Math::round).mapToInt(Math::toIntExact).sum();
        return new Share(amount, (int) (allAmount * 100));
    }

    public static Map<PaymentMethod, Share> mapByStream(List<Payment> paymentList, UserStatistic statistic) {
        Map<PaymentMethod, Share> map = new HashMap<>();
        List<PaymentMethod> paymentMethodList = paymentList.stream().map(Payment::getPaymentMethod).toList();
        for (PaymentMethod method : paymentMethodList) {
            map.put(method, getShareByPaymentMethod(method, paymentList, statistic.getPaymentAmount().doubleValue()));
        }
        return map;

    }

}
