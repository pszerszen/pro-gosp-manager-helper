package com.manager.store.entities;

import com.manager.store.entities.enums.SupplyStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;

/**
 * Supply entity class.
 *
 * @author Piotr
 */
@Entity
@Table(name = "supplys")
public class Supply extends AbstractEntity {

    private static final long serialVersionUID = 5800561080478302387L;

    // TODO discuss about id_supplier (type)
    //    @ManyToOne
    //    @JoinColumn(name = "id_supplier", nullable = false)
    //    private Object supplier;

    @ManyToOne
    @JoinColumn(name = "id_receiver", nullable = false)
    private Store receiver;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "status", columnDefinition = "TINYINT", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private SupplyStatus status;

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Store getReceiver() {
        return receiver;
    }

    public void setReceiver(Store receiver) {
        this.receiver = receiver;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SupplyStatus getStatus() {
        return status;
    }

    public void setStatus(SupplyStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                //.append(supplier)
                .append(receiver)
                .append(quantity)
                .append(product)
                .append(date)
                .append(status)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Supply)) {
            return false;
        }
        Supply other = (Supply) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                //.append(supplier, other.supplier)
                .append(receiver, other.receiver)
                .append(quantity, other.quantity)
                .append(product, other.product)
                .append(date, other.date)
                .append(status, other.status)
                .isEquals();
    }

}
