import {api as basicApi} from "./basicApi.js";
export const api={
    async getStats(fromDate, toDate) {
        if (!(fromDate instanceof Date)) {
            fromDate = new Date(fromDate || "2000-01-01");
        }
        if (!(toDate instanceof Date)) {
            toDate = new Date(toDate || "2100-01-01");
        }
        return basicApi
            .get(`stats?from=${getISODate(fromDate)}&to=${getISODate(toDate)}`);
            // .then((stats)=>console.log(stats));
            // .then((stats) => stats.fromDate=new Date());
    }
};