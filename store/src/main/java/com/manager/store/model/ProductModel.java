package com.manager.store.model;

import com.manager.store.validators.ValidationMessagesConstants;

import javax.validation.constraints.Pattern;

public class ProductModel extends AbstractModel {

    private String name;

    private String model;

    private String producer;

    private boolean active;

    @Pattern(regexp = ValidationMessagesConstants.POSITIVE_PRICE_REGEX)
    private Double purchasePrice;

    @Pattern(regexp = ValidationMessagesConstants.POSITIVE_PRICE_REGEX)
    private Double salesPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }
}
