package com.manager.store.model;

import com.manager.store.validators.ValidationMessagesConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;

public class UserModel extends AbstractModel {

    private Long workerId;

    private String mail;

    private boolean active;

    @Pattern(regexp = ValidationMessagesConstants.DECIMAL_SIX_TWO_REGEX)
    private String bonus;

    @DateTimeFormat(pattern = ValidationMessagesConstants.DATE_ONLY_DATE_PATTERN)
    private String dateFrom;

    @DateTimeFormat(pattern = ValidationMessagesConstants.DATE_ONLY_DATE_PATTERN)
    private String dateUntil;

    private String firstName;

    private String lastName;

    @Pattern(regexp = ValidationMessagesConstants.PRICE_REGEX)
    private String salary;

    private ModelSummary store;

    private UserType type;

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateUntil() {
        return dateUntil;
    }

    public void setDateUntil(String dateUntil) {
        this.dateUntil = dateUntil;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(final String salary) {
        this.salary = salary;
    }

    public ModelSummary getStore() {
        return store;
    }

    public void setStore(final ModelSummary store) {
        this.store = store;
    }

    public UserType getType() {
        return type;
    }

    public void setType(final UserType type) {
        this.type = type;
    }

    public enum UserType {
        Worker, Manager, Admin
    }
}
