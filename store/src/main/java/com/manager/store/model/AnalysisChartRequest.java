package com.manager.store.model;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isAnyBlank;

/**
 * @author Piotr
 */
public class AnalysisChartRequest {

    String type;

    String dateFrom;

    String dateUntil;

    List<Long> shops;

    List<Long> products;

    List<Long> workers;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(final String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(final String dateUntil) {
        this.dateUntil = dateUntil;
    }

    public List<Long> getShops() {
        return shops;
    }

    public void setShops(final List<Long> shops) {
        this.shops = shops;
    }

    public List<Long> getProducts() {
        return products;
    }

    public void setProducts(final List<Long> products) {
        this.products = products;
    }

    public List<Long> getWorkers() {
        return workers;
    }

    public void setWorkers(final List<Long> workers) {
        this.workers = workers;
    }

    public boolean emptyRequest(){
        return isAnyBlank(dateFrom, dateUntil)
                && isEmpty(shops)
                && isEmpty(products)
                && isEmpty(workers);
    }
}
