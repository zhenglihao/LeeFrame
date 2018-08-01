package com.example.lee.leeframe.api.http;

import java.util.List;

/**
 * @Author Lee
 * @Time 2018/7/12
 * @Theme 分页Bean(具体参照 网络请求 做调整)
 */


public class ListResult<T> {

    /**
     * records : [{"clickable":true,"entity":{"id":28,"projectNo":"18042421304800004","projectType":2,"projectName":"This is a test","projectImagePath":"/upload/013/2018/05/7faf850529d34546bbe6be9a8861952e_300x300q1.0.jpg","projectPeriod":"2-3","projectPeriodUnit":"周","maxPriceAmt":22,"minPriceAmt":11,"priceUnit":"万","priceCcy":"USD","identityType":null,"houseAnnualIncreasePercent":null,"houseExpectedRecompense":null,"investDuration":"360","investAnnualRatio":"4545.00","otherNationFlag":true,"otherNationName":"香港","nations":["美国"],"nationIds":[1],"tags":null,"tagIds":null,"favCount":null,"agentCount":null}}]
     * totalRecords : 1
     * page : 1
     * pageSize : 10
     * totalPages : 1
     */

    private int totalRecords;
    private int page;
    private int pageSize;
    private int totalPages;
    private List<T> records;

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
