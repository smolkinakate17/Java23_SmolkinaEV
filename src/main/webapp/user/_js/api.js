const api={
    async getStats(from,to){
        if (!(from instanceof Date)) {
            from = new Date(from);
        }
        if (!(to instanceof Date)) {
            to = new Date(to);
        }
        return mockStats.allStatistic
        .map(
            (s)=>({
                fromDate: s.fromDate,
                toDate: s.toDate,
                allPayments: s.allPayments,
                allAmount: s.allAmount,
                paymentMethodShares: s.paymentMethodShares,
                categoryShares: s.categoryShares,
                topSuppliers: s.topSuppliers
            })
        )
        .filter((s)=>new Date(s.fromDate) >= from && new Date(s.toDate) <= to);
    }
};