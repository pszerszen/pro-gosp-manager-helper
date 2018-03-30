package com.manager.store.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Product entity class.
 *
 * @author Piotr
 */
@Entity
@Table(name = "products")
public class Product extends AbstractEntity {

    private static final long serialVersionUID = -7375808868852522228L;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "model", length = 100, nullable = false)
    private String model;

    @Column(name = "producer", length = 50, nullable = false)
    private String producer;

    @Column(name = "purchase_price", precision = 12, scale = 2, nullable = false)
    private Double purchasePrice;

    @Column(name = "sales_price", precision = 12, scale = 2, nullable = false)
    private Double salesPrice;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "product")
    private Set<Supply> supplies = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Transaction> transactions = new HashSet<>();

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

    public Set<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(Set<Supply> supplies) {
        this.supplies = supplies;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(name)
                .append(model)
                .append(producer)
                .append(purchasePrice)
                .append(salesPrice)
                .append(active)
                .append(supplies)
                .append(transactions)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Product)) {
            return false;
        }
        Product other = (Product) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(name, other.name)
                .append(model, other.model)
                .append(producer, other.producer)
                .append(purchasePrice, other.purchasePrice)
                .append(salesPrice, other.salesPrice)
                .append(active, other.active)
                .append(supplies, other.supplies)
                .append(transactions, other.transactions)
                .isEquals();

    }

}
