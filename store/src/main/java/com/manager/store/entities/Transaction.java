package com.manager.store.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Transaction entity class.
 *
 * @author Piotr
 */
@Entity
@Table(name = "transactions")
public class Transaction extends AbstractEntity {

    private static final long serialVersionUID = -5246656371956745510L;

    @ManyToOne
    @JoinColumn(name = "id_staff", nullable = false)
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_store", nullable = false)
    private Store store;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "date", columnDefinition = "DATETIME", nullable = false)
    private Date date;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(staff)
                .append(product)
                .append(store)
                .append(quantity)
                .append(date)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(staff, other.staff)
                .append(product, other.product)
                .append(store, other.store)
                .append(quantity, other.quantity)
                .append(date, other.date)
                .isEquals();
    }

}
