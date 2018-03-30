package com.manager.store.model;

public class ModelSummary extends AbstractModel {

    private String name;

    private String description;

    public ModelSummary() {
    }

    public ModelSummary(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
