package org.example.statistic.data;

import org.example.entities.Category;
import org.example.entities.Payment;
import org.example.statistic.user.Share;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryShareMapData {
    private static Share getShareByCategory(Category category, List<Payment> paymentList, double allAmount) {
       int amount = paymentList.stream().filter(payment -> payment.getCategories().contains(category))
                .mapToDouble(payment -> payment.getAmount() * 100).mapToLong(Math::round).mapToInt(Math::toIntExact).sum();
        return new Share(amount, (int)allAmount*100);
    }

    public static Map<Category, Share> allPayments() {
        Map<Category, Share> map = new HashMap<>();
        map.put(CategoryData.restaurant, getShareByCategory(CategoryData.restaurant,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(CategoryData.taxi, getShareByCategory(CategoryData.taxi,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(CategoryData.foodstuff, getShareByCategory(CategoryData.foodstuff,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(CategoryData.internetMarket, getShareByCategory(CategoryData.internetMarket,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(CategoryData.clothesAndShoes, getShareByCategory(CategoryData.clothesAndShoes,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(CategoryData.utilities, getShareByCategory(CategoryData.utilities,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(CategoryData.other, getShareByCategory(CategoryData.other,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(CategoryData.luxuries, getShareByCategory(CategoryData.luxuries,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        map.put(CategoryData.entertainment, getShareByCategory(CategoryData.entertainment,
                UserStatisticData.allPayments.getPaymentList(), UserStatisticData.allPayments.getPaymentAmount()));
        return map;
    }

    public static Map<Category, Share> january() {
        Map<Category, Share> map = new HashMap<>();
        map.put(CategoryData.restaurant, getShareByCategory(CategoryData.restaurant,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        map.put(CategoryData.taxi, getShareByCategory(CategoryData.taxi,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        map.put(CategoryData.foodstuff, getShareByCategory(CategoryData.foodstuff,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        map.put(CategoryData.internetMarket, getShareByCategory(CategoryData.internetMarket,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        map.put(CategoryData.clothesAndShoes, getShareByCategory(CategoryData.clothesAndShoes,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        map.put(CategoryData.utilities, getShareByCategory(CategoryData.utilities,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        map.put(CategoryData.other, getShareByCategory(CategoryData.other,
                UserStatisticData.january.getPaymentList(), UserStatisticData.january.getPaymentAmount()));
        return map;
    }

    public static Map<Category, Share> february() {
        Map<Category, Share> map = new HashMap<>();
        map.put(CategoryData.foodstuff, getShareByCategory(CategoryData.foodstuff,
                UserStatisticData.february.getPaymentList(), UserStatisticData.february.getPaymentAmount()));
        map.put(CategoryData.internetMarket, getShareByCategory(CategoryData.internetMarket,
                UserStatisticData.february.getPaymentList(), UserStatisticData.february.getPaymentAmount()));
        map.put(CategoryData.luxuries, getShareByCategory(CategoryData.luxuries,
                UserStatisticData.february.getPaymentList(), UserStatisticData.february.getPaymentAmount()));
        map.put(CategoryData.clothesAndShoes, getShareByCategory(CategoryData.clothesAndShoes,
                UserStatisticData.february.getPaymentList(), UserStatisticData.february.getPaymentAmount()));
        return map;
    }

    public static Map<Category, Share> march() {
        Map<Category, Share> map = new HashMap<>();
        map.put(CategoryData.taxi, getShareByCategory(CategoryData.taxi,
                UserStatisticData.march.getPaymentList(), UserStatisticData.march.getPaymentAmount()));
        map.put(CategoryData.foodstuff, getShareByCategory(CategoryData.foodstuff,
                UserStatisticData.march.getPaymentList(), UserStatisticData.march.getPaymentAmount()));
        map.put(CategoryData.internetMarket, getShareByCategory(CategoryData.internetMarket,
                UserStatisticData.march.getPaymentList(), UserStatisticData.march.getPaymentAmount()));
        map.put(CategoryData.other, getShareByCategory(CategoryData.other,
                UserStatisticData.march.getPaymentList(), UserStatisticData.march.getPaymentAmount()));
        map.put(CategoryData.restaurant, getShareByCategory(CategoryData.restaurant,
                UserStatisticData.march.getPaymentList(), UserStatisticData.march.getPaymentAmount()));
        map.put(CategoryData.entertainment, getShareByCategory(CategoryData.entertainment,
                UserStatisticData.march.getPaymentList(), UserStatisticData.march.getPaymentAmount()));
        return map;
    }

    public static Map<Category, Share> januaryAndFebruary() {
        Map<Category, Share> map = new HashMap<>();
        map.put(CategoryData.restaurant, getShareByCategory(CategoryData.restaurant,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(CategoryData.taxi, getShareByCategory(CategoryData.taxi,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(CategoryData.foodstuff, getShareByCategory(CategoryData.foodstuff,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(CategoryData.internetMarket, getShareByCategory(CategoryData.internetMarket,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(CategoryData.clothesAndShoes, getShareByCategory(CategoryData.clothesAndShoes,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(CategoryData.utilities, getShareByCategory(CategoryData.utilities,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(CategoryData.other, getShareByCategory(CategoryData.other,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        map.put(CategoryData.luxuries, getShareByCategory(CategoryData.luxuries,
                UserStatisticData.januaryAndFebruary.getPaymentList(), UserStatisticData.januaryAndFebruary.getPaymentAmount()));
        return map;
    }

    public static Map<Category, Share> februaryAndMarch() {
        Map<Category, Share> map = new HashMap<>();
        map.put(CategoryData.foodstuff, getShareByCategory(CategoryData.foodstuff,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        map.put(CategoryData.internetMarket, getShareByCategory(CategoryData.internetMarket,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        map.put(CategoryData.luxuries, getShareByCategory(CategoryData.luxuries,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        map.put(CategoryData.clothesAndShoes, getShareByCategory(CategoryData.clothesAndShoes,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        map.put(CategoryData.taxi, getShareByCategory(CategoryData.taxi,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        map.put(CategoryData.other, getShareByCategory(CategoryData.other,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        map.put(CategoryData.restaurant, getShareByCategory(CategoryData.restaurant,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        map.put(CategoryData.entertainment, getShareByCategory(CategoryData.entertainment,
                UserStatisticData.februaryAndMarch.getPaymentList(), UserStatisticData.februaryAndMarch.getPaymentAmount()));
        return map;
    }

}
