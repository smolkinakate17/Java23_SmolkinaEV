import {api} from "./api.js";

document.addEventListener("DOMContentLoaded", () => {
    const sevenDays = document.getElementById("sevenDays");
    const thirtyDays = document.getElementById("thirtyDays");
    const userDays = document.getElementById("userDays");
    const userDate = document.getElementById("userDate");
    const inputFromDate = document.getElementById("input-period_from");
    const inputToDate = document.getElementById("input-period_to");
    const loadingSpinner = document.getElementById("block-loading");
    const alert = document.getElementById("block-alert");

    const stats = document.getElementById("stats");
    userDate.style.display = 'none';

    sevenDays.addEventListener("click", showDates);
    thirtyDays.addEventListener("click", showDates);
    userDays.addEventListener("click", showDates);

    sevenDays.addEventListener("click", loadStats);
    thirtyDays.addEventListener("click", loadStats);
    userDays.addEventListener("click", loadStats);

    loadStats();

    function showDates() {
        if (userDays.checked) {
            userDate.style.display = 'flex';
            inputFromDate.addEventListener("change", loadStats);
            inputToDate.addEventListener("change", loadStats);
        } else {
            userDate.style.display = 'none';
            inputFromDate.removeEventListener("change", loadStats);
            inputToDate.removeEventListener("change", loadStats);
        }
    }

    function loadStats() {
        setLoading(loadingSpinner, true);
        setAlert(alert);
        stats.style.display = "none";
        let fromDate;
        let toDate;
        if (sevenDays.checked) {
            toDate = new Date();
            fromDate = new Date();
            fromDate.setDate(toDate.getDate() - 6);
        } else if (thirtyDays.checked) {
            toDate = new Date();
            fromDate = new Date().setDate(toDate.getDate() - 30);
        } else if (userDays.checked) {
            toDate = inputToDate.value;
            fromDate = inputFromDate.value;
        }

        api.getStats(fromDate, toDate)
            .then(
                (result) => {

                    setStats(result);
                    stats.style.display = "block";

                }
            )
            .catch((err) => {
                console.error("getStats failed", err);
                setAlert(alert, "Произошла ошибка при загрузке статистики");
            })
            .finally(() => setLoading(loadingSpinner, false));

    }

    function setStats(result) {
        const paymentCount = document.getElementById("paymentCount");
        const topSuppliers = document.getElementById("topSuppliers");
        const allAmount = document.getElementById("paymentAmount");
        const paymentMethodShares = document.getElementById("paymentMethodShares");
        const categoryShares = document.getElementById("categoryShares");

        paymentMethodShares.replaceChildren();
        categoryShares.replaceChildren();
        topSuppliers.innerText = "";

        paymentCount.innerText = result.allPayments;


        allAmount.innerText = result.allAmount;


        const suppliers = result.topSuppliers;


        let suppliersText = "";
        if (suppliers.length > 0) {
            for (let i = 0; i < suppliers.length - 1; i++) {

                suppliersText += suppliers[i] + ", ";

            }
            suppliersText += suppliers[suppliers.length - 1];

            topSuppliers.innerText = suppliersText;

        }

        let methods = [];
        methods = result.paymentMethodShares;

        for (let i = 0; i < methods.length; i++) {

            paymentMethodShares.append(setPaymentMethodShare(methods[i]));
        }
        let categories = result.categoryShares;
        // for (let i = 0; i < categories.length; i++) {
        //     console.log(categories[i])
        //     setCategoryShares(categories[i], categoryShares);
        // }
        setCategoryShares(categories, categoryShares);

    }

    function setPaymentMethodShare(result) {
        const methodShare = document.createElement("div");
        methodShare.className = "row";

        const method = document.createElement("div");
        method.className = "col-sm-4";
        const methodText = document.createElement("h6");
        methodText.className = "card-text";
        methodText.innerText = result.method;
        method.append(methodText);
        methodShare.append(method);

        const amount = document.createElement("div");
        amount.className = "col-sm-4";
        const amountText = document.createElement("h6");
        amountText.className = "card-text";
        amountText.innerText = result.amount + "руб";
        amount.append(amountText);
        methodShare.append(amount);

        const percent = document.createElement("div");
        percent.className = "col-sm-4";
        const percentText = document.createElement("h6");
        percentText.className = "card-text";
        percentText.innerText = result.percent + "%";
        percent.append(percentText);
        methodShare.append(percent);

        return methodShare;
    }

    function setCategoryShares(result, block) {
        const count = result.length;
        for (let i = 0; i < result.length; i++) {
            const category = result[i];

            const column = document.createElement("div");
            //column.className="container-fluid";

            const columnColumn = document.createElement("div");
            const height = (category.percent * count) + "px";
            const width = (window.innerWidth / count / 2) + "px";
            columnColumn.setAttribute("style", "height: " + height + "; background-color:" + category.color);
            columnColumn.className = "container-fluid";

            // columnColumn.style.height=category.percent;
            // columnColumn.style.width=window.innerWidth/count;
            // console.log(window.innerWidth/count);
            //columnColumn.style.color=category.category.color;

            const columnTitle = document.createElement("h6");
            columnTitle.innerText = category.title + " " + category.amount;
            columnTitle.className = "p-3"

            const columnPercent = document.createElement("h6");
            columnPercent.innerText = category.percent + "%";

            column.append(columnPercent);
            column.append(columnColumn);
            column.append(columnTitle);

            block.append(column);
        }


    }

})