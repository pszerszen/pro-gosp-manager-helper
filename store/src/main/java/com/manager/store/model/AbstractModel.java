package com.manager.store.model;

import javax.validation.constraints.NotNull;

public class AbstractModel {

    @NotNull
    protected Long id;


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }
}
