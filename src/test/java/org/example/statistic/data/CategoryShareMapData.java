package org.example.statistic.data;

import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.entities.PaymentCategory;
import org.example.statistic.user.Share;
import org.example.statistic.user.UserStatistic;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CategoryShareMapData {
    private static Share getShareByCategory(Category category, List<PaymentCategory> paymentCategoriesList, double allAmount) {
        int amount = paymentCategoriesList.stream().filter(paymentCategory -> paymentCategory.getCategory().equals(category))
                .mapToDouble(paymentCategory -> paymentCategory.getPayment().getAmount().doubleValue() * 100)
                .mapToLong(Math::round).mapToInt(Math::toIntExact).sum();
        return new Share(amount, (int) (allAmount * 100));
    }

    public static Map<Category, Share> mapByStream(List<Payment> paymentList, UserStatistic statistic) {
        Map<Category, Share> map = new HashMap<>();
        List<Category> categoryList = paymentList.stream().map(Payment::getPaymentCategories)
                .flatMap(Collection::stream).map(PaymentCategory::getCategory).distinct().toList();
        List<PaymentCategory> paymentCategoryList = paymentList.stream().map(Payment::getPaymentCategories)
                .flatMap(Collection::stream).toList();
        for (Category category : categoryList) {
            map.put(category, getShareByCategory(category, paymentCategoryList, statistic.getPaymentAmount().doubleValue()));
        }

        return map;
    }


}
