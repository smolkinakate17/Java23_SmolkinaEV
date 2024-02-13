package org.example.statistic.user;

import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.entities.PaymentMethod;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class UserStatistic {
    private final Period period;
    private final List<Payment> paymentList;

    public UserStatistic(Period period, List<Payment> paymentList) {
        this.period = period;
        this.paymentList = paymentList;
    }

    public int getPaymentCount(){
        return paymentList.size();
    }
    public double getPaymentAmount(){
        return paymentList.stream().mapToDouble(Payment::getAmount).sum();
    }
    public Map<PaymentMethod,Share> getPaymentMethodSharesMap(){
        Map<PaymentMethod,Share> paymentMethodShareMap=new HashMap<>();
        List<PaymentMethod> paymentMethodList=paymentList.stream().map(Payment::getPaymentMethod)
                .distinct().toList();
        double allAmount=paymentList.stream().mapToDouble(Payment::getAmount).sum();
        for(PaymentMethod method:paymentMethodList){
            double amount=paymentList.stream().filter(payment -> payment.getPaymentMethod()==method)
                    .mapToDouble(Payment::getAmount).sum();
            double percent=amount/allAmount*100;
            Share share=new Share(amount,percent);
            paymentMethodShareMap.put(method,share);
        }
        return paymentMethodShareMap;

    }
    public Map<Category,Share> getCategorySharesMap(){
        Map<Category,Share> categoryShareMap=new HashMap<>();
        List<Category> categoryList=paymentList.stream().map(Payment::getCategories)
                .flatMap(Collection::stream).distinct().toList();
        double allAmount=paymentList.stream().mapToDouble(Payment::getAmount).sum();
        for(Category category:categoryList){
            double amount=paymentList.stream()
                    .filter(payment -> payment.getCategories().contains(category))
                    .mapToDouble(Payment::getAmount).sum();
            double percent=amount/allAmount*100;
            Share share=new Share(amount,percent);
            categoryShareMap.put(category,share);
        }
        return categoryShareMap;
    }
    public String[] getTopThreeSuppliers(){

        Map<String,Long> supplierMap=paymentList.stream().map(Payment::getSupplier)
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        List<String> topList=supplierMap.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue))
                .limit(3).map(Map.Entry::getKey).toList();
        return topList.toArray(new String[3]);

    }
}
