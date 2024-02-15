package org.example.statistic.user;

import lombok.Getter;
import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.entities.PaymentMethod;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public final class UserStatistic {
    private final List<Payment> paymentList;

    public UserStatistic(Period period, List<Payment> paymentList) {
        this.paymentList = paymentList.stream()
                .filter(payment -> period.validateDate(payment.getPaymentDateTime().toLocalDate())).toList();
    }

    public int getPaymentCount() {
        return paymentList.size();
    }

    public double getPaymentAmount() {
        return paymentList.stream().mapToDouble(payment -> payment.getAmount() * 100).mapToLong(Math::round).sum() / 100.0;
    }

    public Map<PaymentMethod, Share> getPaymentMethodSharesMap() {
        Map<PaymentMethod, Share> paymentMethodShareMap = new HashMap<>();
        List<PaymentMethod> paymentMethodList = paymentList.stream().map(Payment::getPaymentMethod)
                .distinct().toList();
        double allAmount = getPaymentAmount();
        for (PaymentMethod method : paymentMethodList) {
            double amount = paymentList.stream().filter(payment -> payment.getPaymentMethod() == method)
                    .mapToDouble(payment -> payment.getAmount() * 100).mapToLong(Math::round).sum() / 100.0;
            double percent = amount / allAmount * 10000;
            Share share = new Share(amount, Math.round(percent) / 100.0);
            paymentMethodShareMap.put(method, share);
        }
        return paymentMethodShareMap;

    }

    public Map<Category, Share> getCategorySharesMap() {
        Map<Category, Share> categoryShareMap = new HashMap<>();
        List<Category> categoryList = paymentList.stream().map(Payment::getCategories)
                .flatMap(Collection::stream).distinct().toList();
        double allAmount = getPaymentAmount();
        for (Category category : categoryList) {
            double amount = paymentList.stream()
                    .filter(payment -> payment.getCategories().contains(category))
                    .mapToDouble(payment -> payment.getAmount() * 100).mapToLong(Math::round).sum() / 100.0;
            double percent = amount / allAmount * 10000;
            Share share = new Share(amount, Math.round(percent) / 100.0);
            categoryShareMap.put(category, share);
        }
        return categoryShareMap;
    }

    public String[] getTopThreeSuppliers() {

        Map<String, Long> supplierMap = paymentList.stream().map(Payment::getSupplier)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        List<String> topList = supplierMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3).map(Map.Entry::getKey).toList();
        // на выходе получаем топ три продавца, если у нескольких продавцов одинаковое количество покупок, то они отсортированы по алфавиту
        return topList.toArray(new String[3]);

    }
}
