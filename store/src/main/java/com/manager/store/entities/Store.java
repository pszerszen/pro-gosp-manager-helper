package com.manager.store.entities;

import com.manager.store.entities.enums.StoreType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Store entity class.
 *
 * @author Piotr
 */
@Entity
@Table(name = "store")
public class Store extends AbstractEntity {

    private static final long serialVersionUID = -6823084943637669395L;

    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "phone", length = 30, nullable = false)
    private String phone;

    @Column(name = "type", columnDefinition = "TINYINT", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StoreType type;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "store")
    private Set<Transaction> transactions = new HashSet<>();

    @OneToMany(mappedBy = "store")
    private Set<Staff> employees = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public StoreType getType() {
        return type;
    }

    public void setType(StoreType type) {
        this.type = type;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Staff> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Staff> employees) {
        this.employees = employees;
    }


    public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(name)
                .append(address)
                .append(phone)
                .append(type)
                .append(transactions)
                .append(employees)
                .append(active)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Store)) {
            return false;
        }
        Store other = (Store) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(name, other.name)
                .append(address, other.address)
                .append(phone, other.phone)
                .append(type, other.type)
                .append(transactions, other.transactions)
                .append(employees, other.employees)
                .append(active, other.active)
                .isEquals();
    }

}
