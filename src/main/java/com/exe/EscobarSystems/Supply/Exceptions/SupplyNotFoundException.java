package com.exe.EscobarSystems.Supply.Exceptions;

public class SupplyNotFoundException extends RuntimeException {

    public SupplyNotFoundException(Long id){
        super(String.format("Could not find Supply with id: %s", id));
    }

    public SupplyNotFoundException(String name){
        super(String.format("Could not find Supply with name: %s", name));
    }
}
