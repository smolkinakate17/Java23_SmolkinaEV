package org.example.statistic.data;

import org.example.entities.Category;

import java.util.ArrayList;
import java.util.List;


public final class CategoryData {
    public static final Category foodstuff = new Category("Продукты", "Продукты питания", "red");
    public static final Category restaurant = new Category("Рестораны", "Кафе, рестораны, столовые и тд", "yellow");
    public static final Category clothesAndShoes = new Category("Одежда и обувь", "Магазины одежды и обуви", "green");
    public static final Category taxi = new Category("Такси", "Поездки на такси", "blue");
    public static final Category internetMarket = new Category("Интернет-магазины", "Покупки на сайтах интернет-магазинов", "lightblue");
    public static final Category utilities = new Category("Коммунальные платежи", "Оплата коммунальных платежей", "orange");
    public static final Category study = new Category("Обучение", "Оплата за обучающие курсы и прочее обучение", "violet");
    public static final Category entertainment = new Category("Развлечения", "Покупки билетов в кинотеатры, театры, музеи и тд", "lightgreen");
    public static final Category luxuries = new Category("Предметы роскоши", "Покупки в ювелирных магазинах, антикварных магазинах и тд", "pink");
    public static final Category other = new Category("Прочее", "Прочие покупки", "darkred");
    public static List<Category> categoryList() {
        List<Category> list = new ArrayList<>();
        list.add(foodstuff);
        list.add(restaurant);
        list.add(clothesAndShoes);
        list.add(taxi);
        list.add(internetMarket);
        list.add(utilities);
        list.add(study);
        list.add(entertainment);
        list.add(luxuries);
        list.add(other);
        return list;
    }
}
