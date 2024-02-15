package org.example.statistic.data;

import org.example.entities.Payment;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class PaymentManagerData {
    public static final List<Payment> januaryFifteen = Arrays.asList(
            new Payment(2,
                    LocalDateTime.of(2024, 1, 15, 8, 0),
                    "ЯндексТакси", 218,
                    PaymentMethodData.card, Collections.singletonList(CategoryData.taxi)),
            new Payment(3,
                    LocalDateTime.of(2024, 1, 15, 13, 45),
                    "Пятерочка", 390.50,
                    PaymentMethodData.card, Collections.singletonList(CategoryData.foodstuff)),
            new Payment(4,
                    LocalDateTime.of(2024, 1, 15, 14, 50),
                    "Wildberries", 647,
                    PaymentMethodData.card, Arrays.asList(CategoryData.internetMarket, CategoryData.clothesAndShoes)),
            new Payment(5,
                    LocalDateTime.of(2024, 1, 15, 20, 30),
                    "ЭнергосбытПлюс", 834.57,
                    PaymentMethodData.card, Collections.singletonList(CategoryData.utilities))
            );
    public static final List<Payment> marchSeventeen=Arrays.asList(
            new Payment(15,
                    LocalDateTime.of(2024, 3, 17, 8, 20),
                    "ЯндексТакси", 452,
                    PaymentMethodData.card, Collections.singletonList(CategoryData.taxi)),
            new Payment(16,
                    LocalDateTime.of(2024, 3, 17, 10, 20),
                    "Пятерочка", 3294.60,
                    PaymentMethodData.cash, Arrays.asList(CategoryData.foodstuff, CategoryData.internetMarket))
    );
    public static final List<Payment> marchEighteen=Arrays.asList(
            new Payment(17,
                    LocalDateTime.of(2024, 3, 18, 12, 45),
                    "У Палыча", 472.50,
                    PaymentMethodData.card, Collections.singletonList(CategoryData.restaurant)),
            new Payment(18,
                    LocalDateTime.of(2024, 3, 18, 13, 15),
                    "Магнит", 293.30,
                    PaymentMethodData.cash, Collections.singletonList(CategoryData.foodstuff)),
            new Payment(19,
                    LocalDateTime.of(2024, 3, 18, 20, 30),
                    "КиноМакс", 450,
                    PaymentMethodData.card, Collections.singletonList(CategoryData.entertainment))
    );
    public static final List<Payment> januaryTwenty= List.of(
            new Payment(7,
                    LocalDateTime.of(2024, 1, 20, 22, 45),
                    "Самокат", 1265.45,
                    PaymentMethodData.card, Arrays.asList(CategoryData.foodstuff, CategoryData.internetMarket))
    );
    public static final List<Payment> januaryFirst=List.of(
            new Payment(1,
                    LocalDateTime.of(2024, 1, 1, 18, 40),
                    "Шаурма у Ашота", 350,
                    PaymentMethodData.cash, Collections.singletonList(CategoryData.restaurant))
    );
}
