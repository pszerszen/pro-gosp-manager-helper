package com.manager.store.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

/**
 * @author Piotr
 */
@Entity
@Table(name = "product_state")
public class ProductState extends AbstractEntity {

    private static final long serialVersionUID = -755933715750023162L;

    @ManyToOne
    @JoinColumn(name = "id_product", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_store", nullable = false)
    private Store store;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Version
    private Long version;

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(product)
                .append(store)
                .append(quantity)
                .append(version)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ProductState)) {
            return false;
        }
        ProductState other = (ProductState) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(product, other.product)
                .append(store, other.store)
                .append(quantity, other.quantity)
                .append(version, other.version)
                .isEquals();
    }


}
