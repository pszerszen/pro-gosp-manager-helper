package com.manager.store.model;

import com.manager.store.entities.enums.SupplyStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SupplyModel extends AbstractModel {

    @Valid
    private ModelSummary store;

    @Valid
    private ModelSummary product;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private String date;

    @NotNull
    private SupplyStatus status;

    @Size(min = 1)
    private int quantity;

    public ModelSummary getStore() {
        return store;
    }

    public void setStore(ModelSummary store) {
        this.store = store;
    }

    public ModelSummary getProduct() {
        return product;
    }

    public void setProduct(ModelSummary product) {
        this.product = product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SupplyStatus getStatus() {
        return status;
    }

    public void setStatus(SupplyStatus status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}
