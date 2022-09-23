package com.exe.EscobarSystems.Supply.Exceptions;

public class SupplySupplierIsNullException extends RuntimeException{

    public SupplySupplierIsNullException(){
        super("Supplier should not be empty");
    }
}
