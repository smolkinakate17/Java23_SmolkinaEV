package org.example.statistic.data;


import org.example.entities.PaymentMethod;

import java.util.ArrayList;
import java.util.List;


public final class PaymentMethodData {
    public static final PaymentMethod cash = new PaymentMethod("Наличные");
    public static final PaymentMethod card = new PaymentMethod("Дебетовая карта");
    public static final PaymentMethod creditCard = new PaymentMethod("Кредитная карта");
    public static final PaymentMethod bankDeposit = new PaymentMethod("Банковский вклад");
    public static final PaymentMethod other = new PaymentMethod("Другое");
    public static List<PaymentMethod> paymentMethodList(){
        List<PaymentMethod> list=new ArrayList<>();
        list.add(cash);
        list.add(card);
        list.add(creditCard);
        list.add(bankDeposit);
        list.add(other);
        return list;
    }
}
