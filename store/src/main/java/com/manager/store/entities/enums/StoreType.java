package com.manager.store.entities.enums;

public enum StoreType {
    STATIONARY("Stationary"), 
    ONLINE("On-line");

    private String description;

    private StoreType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StoreType fromDescription(final String description){
        for(StoreType type : values()){
            if(description.equals(type.getDescription())){
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("The StoreType with description: %s does not exist.", description));
    }
}
