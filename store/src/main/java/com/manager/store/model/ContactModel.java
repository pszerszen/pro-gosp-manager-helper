package com.manager.store.model;

/**
 * @author Piotr
 */
public class ContactModel {

    String mail;

    String name;

    public ContactModel(final String mail, final String name) {
        this.mail = mail;
        this.name = name;
    }

    public ContactModel() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
