package com.exe.EscobarSystems.Supplier.Exceptions;

public class SupplierNameIsNullException extends RuntimeException{

    public SupplierNameIsNullException(){
        super("Supplier name should not be empty");
    }
}
