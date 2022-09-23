package com.exe.EscobarSystems.Supplier.Exceptions;

public class SupplierNotFoundException extends RuntimeException{

    public SupplierNotFoundException(Long id){
        super(String.format("Could not find Supplier with id: %s", id));
    }
    public SupplierNotFoundException(String name){
        super(String.format("Could not find Supplier with name: %s", name));
    }

}
