const categories={
    food: {
        title:"Продукты",
        description:"Продукты питания",
        color:"red"
    },
    restaurant: {
        title:"Рестораны",
        description:"Рестораны и кафе",
        color:"green"
    },
    clothesAndShoes:{
        title:"Одежда и обувь",
        description: "Одежда и обувь",
        color:"purple"
    },
    other:{
        title:"Другое",
        description: "Другое",
        color:"grey"
    }
};

const methods={
    cash:{
        title: "Наличные"
    },
    card:{
        title:"Карта"
    },
    creditCard:{
        title:"Кредитная карта"
    },
    other:{
        title:"Другое"
    }
};



const mockStats={
   allStatistic:[
        {
            fromDate:"2024-01-01",
            toDate:"2024-01-31",
            allPayments: 10,
            allAmount:1000,
            paymentMethodShares: [
                {
                    method: methods.card,
                    amount: 300,
                    percent: 30
                },
                {
                    method: methods.cash,
                    amount: 700,
                    percent: 70
                }
            ],
            categoryShares: [
                {
                    category: categories.food,
                    amount: 100,
                    percent: 10
                },
                {
                    category: categories.clothesAndShoes,
                    amount: 300,
                    percent: 30
                },
                {
                    category: categories.other,
                    amount: 700,
                    percent: 70
                }
            ],
            topSuppliers:[
                {
                    title:"first"
                },
                {
                    title:"second"
                },
                {
                    title:"third"
                }

            ]
        },
        {
            fromDate: "2024-03-03",
            toDate:"2024-03-04",
            allPayments: 20,
            allAmount: 2000,
            paymentMethodShares:[
                {
                    method: methods.creditCard,
                    amount: 1000,
                    percent: 50
                },
                {
                    method: methods.other,
                    amount: 1000,
                    percent: 50
                }
            ],
            categoryShares:[
                {
                    category: categories.restaurant,
                    amount: 2000,
                    percent: 100
                }
            ],
            topSuppliers:[
                {
                    title:"first2"
                },
                {
                    title:"second2"
                },
                {
                    title:"third2"
                }

            ]
        }
    ]
};