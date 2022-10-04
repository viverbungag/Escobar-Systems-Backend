package com.exe.EscobarSystems.Order;

public enum PaymentStatus {
    PAID("PAID"),
    UNPAID("UNPAID");

    private final String string;

    PaymentStatus(final String string){
        this.string = string;
    }

    @Override
    public String toString(){
        return string;
    }
}
