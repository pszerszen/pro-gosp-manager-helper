package com.manager.store.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;

public class TransactionModel extends AbstractModel {

    @Valid
    private ModelSummary staff;

    @Valid
    private ModelSummary product;

    @Valid
    private ModelSummary store;

    @Min(value = 1)
    private int quantity = 1;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String date;

    public ModelSummary getStaff() {
        return staff;
    }

    public void setStaff(ModelSummary staff) {
        this.staff = staff;
    }

    public ModelSummary getProduct() {
        return product;
    }

    public void setProduct(ModelSummary product) {
        this.product = product;
    }

    public ModelSummary getStore() {
        return store;
    }

    public void setStore(ModelSummary store) {
        this.store = store;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
