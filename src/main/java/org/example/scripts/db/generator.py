import random


def extra_zero(value):
    if len(value) == 1:
        value = "0" + value
    return value


def new_datetime():
    s = "2024-"
    month = str(random.randint(1, 12))
    day = ""
    if month == "2":
        day = str(random.randint(1, 28))
    else:
        day = str(random.randint(1, 30))
    month = extra_zero(month)
    day = extra_zero(day)
    hour = str(random.randint(0, 23))
    hour = extra_zero(hour)
    minute = str(random.randint(0, 59))
    minute = extra_zero(minute)
    sec = str(random.randint(0, 59))
    sec = extra_zero(sec)
    return s + str(month) + "-" + str(day) + " " + hour + ":" + minute + ":" + sec


def category_for_payment_category(payment_item):
    l = suppliers[payment_item.supplier]

    count = random.randint(1, len(l))
    result = [0] * count
    random.shuffle(l)
    for j in range(count):
        result[j] = l[j].id
    return result


class Category:
    def __init__(self, title, description, color, id):
        self.id = id
        self.title = title
        self.description = description
        self.color = color

    def insert_category(self):
        return "('" + self.title + "', '" + self.description + "', '" + self.color + "')"

    def insert_payment(self):
        return self.id


class Payment_Method:
    def __init__(self, title, id):
        self.id = id
        self.title = title

    def inser_method(self):
        return "('" + self.title + "')"

    def __str__(self):
        return str(self.id)


class Payment:
    def __init__(self, id):
        self.id = id
        self.supplier = random.choice(list(suppliers.keys()))
        self.amount = round(random.uniform(100, 10000), 2)
        self.method = random.choice(methods)
        self.datetime = new_datetime()

    def insert(self):
        return "('" + self.datetime + "', '" + self.supplier + "', " + str(self.amount) + ", " + str(self.method) + ")"


class Payment_category:
    def __init__(self, p, category_id):
        self.payment_id = p.id
        self.category_id = category_id

    def __str__(self):
        return "(" + str(self.payment_id) + ", " + str(self.category_id) + ")"


category = [
    Category("Супермаркеты", "Покупки в сумеркатетах и продуктовых магазинах", "#00bfff", 1),
    Category("Такси", "Оплата поездок на такси", "#fde910", 2),
    Category("Переводы", "Переводы другим людям", "#87cefa", 3),
    Category("Книги", "Покупки в книжных магазинах и в приложениях для чтения книг", "#e0b0ff", 4),
    Category("Аптеки", "Покупки лекарств", "#25f56a", 5),
    Category("Электроника и техника", "Покупки в магазинах электроники и техники", "#8000ff", 6),
    Category("Фастфуд", "Покупки в ресторанах быстрого питания", "#ffa500", 7),
    Category("Связь", "Оплата мобильной связи и интернета", "#0099cc", 8),
    Category("Общественный транспорт", "Оплата поездок на общественном транспорте", "#ff6666", 9),
    Category("Транспорт", "Оплата поездок на различном транспорте", "#ff3333", 10),
    Category("Услуги банка", "Оплата различных банковских услуг", "#e4a010", 11),
    Category("Кино", "Покупка билетов в кино и покупка фильмов на стриминговых площадках", "#f87dfa", 12),
    Category("Подписки", "Оплата подписок на различных сервисах", "#ccccff", 13),
    Category("Медицина", "Оплата за услуги врача", "#77dd77", 14),
    Category("Косметика", "Покупки в магазинах косметки", "#ff9baa", 15),
    Category("Дом и ремонт", "Покупки в строительных магазинах", "#7b68ee", 16),
    Category("Рестораны", "Покупки в барах, кафе и ресторанах", "#37ad5f", 17),
    Category("Одежда и обувь", "Покупки в магазинах одежды и обуви", "#afdafc", 18),
    Category("Образование", "Покупки образовательных курсов и плата за образование", "#4169e1", 19),
    Category("Красота", "Опалата услуг в салонах красоты", "#fdbcb4", 20),
    Category("ЖКХ", "Оплата коммунальных услуг и ЖКХ", "#122faa", 21),
    Category("Животные", "Покупки в зоомагазинах и оплата услуг ветеринара", "#cda434", 22),
    Category("Театр", "Покупка билетов в театр", "#b00000", 23),
    Category("Музей", "Покупка билетов на выставки и в музеи", "#ed0744", 24),
    Category("Развлечения", "Оплата различных развлечений", "#2a14a8", 25),
    Category("Другое", "Прочие покупки", "grey", 26)
]

methods = [
    Payment_Method("Наличные", 1),
    Payment_Method("Дебетова карта", 2),
    Payment_Method("Кредитная карта", 3),
    Payment_Method("Банковский вклад", 4),
    Payment_Method("Другое", 5)
]

suppliers = {
    "OZON": [category[3], category[5], category[14], category[17], category[15], category[22], category[25]],
    "Wildberries": [category[3], category[5], category[14], category[17], category[15], category[22], category[25]],
    "ЯндексТакси": [category[1]],
    "Uber": [category[1]],
    "Золотое Яблоко": [category[14]],
    "Летуаль": [category[14]],
    "РивГош": [category[14]],
    "Пятерочка": [category[0]],
    "Магнит": [category[0]],
    "Перекресток": [category[0]],
    "Ашан": [category[0]],
    "Лента": [category[0]],
    "Самокат": [category[0]],
    "DNS": [category[5]],
    "СберМегаМаркет": [category[3], category[5], category[14], category[17], category[15], category[22], category[25]],
    "ЯндексМаркет": [category[3], category[5], category[14], category[17], category[15], category[22], category[25]],
    "ЛеруаМерлен": [category[15]],
    "Аптека Вита": [category[4]],
    "Аптека Апрель": [category[4]],
    "Читай город": [category[3]],
    "Чакона": [category[3]],
    "Вкусно и Точка": [category[6]],
    "KFC": [category[6]],
    "BurgerKing": [category[6]],
    "Додо пицца": [category[6]],
    "Invitro": [category[13]],
    "Наука": [category[13]],
    "Медгард": [category[13]],
    "Мать и дитя": [category[13]],
    "КиноМакс": [category[11]],
    "Ivi": [category[11], category[12]],
    "ЯндексМузыка": [category[12]],
    "Кинопоиск": [category[12]],
    "Литрес": [category[12], category[3]],
    "Хокку": [category[16]],
    "Патари": [category[16]],
    "Перчини": [category[16]],
    "Мегафон": [category[7]],
    "МТС": [category[7]],
    "Теле2": [category[7]],
    "Парикмахерская у Натальи": [category[19]],
    "Парикмахерская у Светланы": [category[19]],
}

payments = []

payment_categories = []


def insert_categories():
    print("INSERT INTO public.category (title,description,color) VALUES")
    for category_item in range(len(category) - 2):
        print(category[category_item].insert_category() + ",")
    print(category[len(category) - 1].insert_category() + ";")


def insert_methods():
    print("INSERT INTO public.payment_method (title) VALUES")
    for method_item in range(len(methods) - 2):
        print(methods[method_item].inser_method() + ",")
    print(methods[len(methods) - 1].inser_method() + ";")


def insert_payments():
    print("INSERT INTO public.payment (payment_date_time,supplier,amount,payment_method) VALUES")
    for i in range(99):
        p = Payment(i + 1)
        payments.append(p)
        print(p.insert() + ",")
    p = Payment(100)
    payments.append(p)
    print(p.insert() + ";")


def insert_payments_categories():
    print("insert into public.payment_category(payment_id, category_ig) values")
    for payment_item in payments:
        c_list = category_for_payment_category(payment_item)
        for c in c_list:
            payment_categories.append(Payment_category(payment_item, c))

    for pc in range(len(payment_categories) - 2):
        print(str(payment_categories[pc]) + ",")
    print(str(payment_categories[len(payment_categories) - 1]) + ";")


insert_categories()
insert_methods()
insert_payments()
insert_payments_categories()
