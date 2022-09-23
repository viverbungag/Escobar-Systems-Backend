package com.exe.EscobarSystems.SupplyCategory.Exceptions;

public class SupplyCategoryNotFoundException extends RuntimeException{

    public SupplyCategoryNotFoundException(Long id){
        super(String.format("Could not find Supply Category with id: %s", id));
    }

    public SupplyCategoryNotFoundException(String name){
        super(String.format("Could not find Supply Category with name: %s", name));
    }
}
