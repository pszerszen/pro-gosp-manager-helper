package com.manager.store.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Staff entity class.
 *
 * @author Piotr
 */
@Entity
@Table(name = "staff")
public class Staff extends AbstractEntity {

    private static final long serialVersionUID = -6351444387943472389L;

    @ManyToOne
    @JoinColumn(name = "id_store", nullable = false)
    private Store store;

    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "date_from", columnDefinition = "DATE", nullable = false)
    private Date dateFrom;

    @Column(name = "date_until", columnDefinition = "DATE", nullable = true)
    private Date dateUntil;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "salary", nullable = false)
    private Integer salary;

    @Column(name = "bonus",precision = 12, scale = 2, nullable = true)
    private Double bonus;

    @OneToMany(mappedBy = "staff")
    private Set<Transaction> transactions;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(Date dateUntil) {
        this.dateUntil = dateUntil;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(store)
                .append(firstName)
                .append(lastName)
                .append(dateFrom)
                .append(dateUntil)
                .append(active)
                .append(transactions)
                .append(salary)
                .append(bonus)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) obj;

        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(store, other.store)
                .append(firstName, other.firstName)
                .append(lastName, other.lastName)
                .append(dateFrom, other.dateFrom)
                .append(dateUntil, other.dateUntil)
                .append(active, other.active)
                .append(transactions, other.transactions)
                .append(salary, other.salary)
                .append(bonus, other.bonus)
                .isEquals();
    }

}
