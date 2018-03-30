package com.manager.store.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class MessageModel extends AbstractModel {

    @NotEmpty
    private String messageContent;

    @NotEmpty
    private List<String> toMails;

    private List<String> ccMails;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public List<String> getToMails() {
        return toMails;
    }

    public void setToMails(List<String> toMails) {
        this.toMails = toMails;
    }

    public List<String> getCcMails() {
        return ccMails;
    }

    public void setCcMails(List<String> ccMails) {
        this.ccMails = ccMails;
    }

}
