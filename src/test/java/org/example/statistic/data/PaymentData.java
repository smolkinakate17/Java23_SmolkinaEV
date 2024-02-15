package org.example.statistic.data;

import org.example.entities.Payment;

import java.time.LocalDateTime;
import java.util.*;

public class PaymentData {

    private static final Payment payment1 = new Payment(1,
            LocalDateTime.of(2024, 1, 1, 18, 40),
            "Шаурма у Ашота", 350,
            PaymentMethodData.cash, Collections.singletonList(CategoryData.restaurant));
    private static final Payment payment2 = new Payment(2,
            LocalDateTime.of(2024, 1, 15, 8, 0),
            "ЯндексТакси", 218,
            PaymentMethodData.card, Collections.singletonList(CategoryData.taxi));
    private static final Payment payment3 = new Payment(3,
            LocalDateTime.of(2024, 1, 15, 13, 45),
            "Пятерочка", 390.50,
            PaymentMethodData.card, Collections.singletonList(CategoryData.foodstuff));
    private static final Payment payment4 = new Payment(4,
            LocalDateTime.of(2024, 1, 15, 14, 50),
            "Wildberries", 647,
            PaymentMethodData.card, Arrays.asList(CategoryData.internetMarket, CategoryData.clothesAndShoes));
    private static final Payment payment5 = new Payment(5,
            LocalDateTime.of(2024, 1, 15, 20, 30),
            "ЭнергосбытПлюс", 834.57,
            PaymentMethodData.card, Collections.singletonList(CategoryData.utilities));
    private static final Payment payment6 = new Payment(6,
            LocalDateTime.of(2024, 1, 27, 16, 45),
            "DNS", 5987.50,
            PaymentMethodData.bankDeposit, Collections.singletonList(CategoryData.other));
    private static final Payment payment7 = new Payment(7,
            LocalDateTime.of(2024, 1, 20, 22, 45),
            "Самокат", 1265.45,
            PaymentMethodData.card, Arrays.asList(CategoryData.foodstuff, CategoryData.internetMarket));
    private static final Payment payment8 = new Payment(8,
            LocalDateTime.of(2024, 1, 28, 15, 20),
            "ЯндексТакси", 453,
            PaymentMethodData.cash, Collections.singletonList(CategoryData.taxi));
    private static final Payment payment9 = new Payment(9,
            LocalDateTime.of(2024, 2, 3, 10, 15),
            "Магнит", 2543.67,
            PaymentMethodData.card, Arrays.asList(CategoryData.foodstuff, CategoryData.internetMarket));
    private static final Payment payment10 = new Payment(10,
            LocalDateTime.of(2024, 2, 26, 13, 45),
            "Золотая рыбка", 5789,
            PaymentMethodData.creditCard, Collections.singletonList(CategoryData.luxuries));
    private static final Payment payment11 = new Payment(11,
            LocalDateTime.of(2024, 2, 28, 10, 50),
            "Wildberries", 1234,
            PaymentMethodData.card, Arrays.asList(CategoryData.clothesAndShoes, CategoryData.internetMarket));
    private static final Payment payment12 = new Payment(12,
            LocalDateTime.of(2024, 3, 2, 14, 50),
            "ЯндексТакси", 376,
            PaymentMethodData.card, Collections.singletonList(CategoryData.taxi));
    private static final Payment payment13 = new Payment(13,
            LocalDateTime.of(2024, 3, 5, 18, 50),
            "Пятерочка", 2976.80,
            PaymentMethodData.card, Arrays.asList(CategoryData.foodstuff, CategoryData.internetMarket));
    private static final Payment payment14 = new Payment(14,
            LocalDateTime.of(2024, 3, 15, 20, 10),
            "Wildberries", 583,
            PaymentMethodData.card, Arrays.asList(CategoryData.internetMarket, CategoryData.other));
    private static final Payment payment15 = new Payment(15,
            LocalDateTime.of(2024, 3, 17, 8, 20),
            "ЯндексТакси", 452,
            PaymentMethodData.card, Collections.singletonList(CategoryData.taxi));
    private static final Payment payment16 = new Payment(16,
            LocalDateTime.of(2024, 3, 17, 10, 20),
            "Пятерочка", 3294.60,
            PaymentMethodData.cash, Arrays.asList(CategoryData.foodstuff, CategoryData.internetMarket));
    private static final Payment payment17 = new Payment(17,
            LocalDateTime.of(2024, 3, 18, 12, 45),
            "У Палыча", 472.50,
            PaymentMethodData.card, Collections.singletonList(CategoryData.restaurant));
    private static final Payment payment18 = new Payment(18,
            LocalDateTime.of(2024, 3, 18, 13, 15),
            "Магнит", 293.30,
            PaymentMethodData.cash, Collections.singletonList(CategoryData.foodstuff));
    private static final Payment payment19 = new Payment(19,
            LocalDateTime.of(2024, 3, 18, 20, 30),
            "КиноМакс", 450,
            PaymentMethodData.card, Collections.singletonList(CategoryData.entertainment));
    private static final Payment payment20 = new Payment(20,
            LocalDateTime.of(2024, 3, 20, 10, 30),
            "Пятерочка", 1673.89,
            PaymentMethodData.card, Arrays.asList(CategoryData.foodstuff, CategoryData.internetMarket));
    public static final List<Payment> paymentList = Arrays.asList(payment1, payment2, payment3, payment4, payment5,
            payment6, payment7, payment8, payment9, payment10, payment11, payment12, payment13, payment14, payment15,
            payment16, payment17, payment18, payment19, payment20);


}
