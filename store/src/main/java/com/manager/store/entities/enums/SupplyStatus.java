package com.manager.store.entities.enums;


public enum SupplyStatus {
    PLACED,
    PENDING,
    DONE,
    CANCELED;

    public boolean isAnyOf(SupplyStatus... supplyStatuses){
        for(SupplyStatus status : supplyStatuses){
            if(status.equals(this)){
                return true;
            }
        }
        return false;
    }
}
