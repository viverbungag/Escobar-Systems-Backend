package com.exe.EscobarSystems.Order;

public enum ServingType {

    DINE_IN("DINE_IN"),
    TAKE_OUT("TAKE_OUT");

    private final String string;

    ServingType(final String string){
        this.string = string;
    }

    @Override
    public String toString(){
        return string;
    }
}
