package com.exe.EscobarSystems.SupplyCategory.Exceptions;

public class SupplyCategoryNameIsNullException extends RuntimeException{

    public SupplyCategoryNameIsNullException(){
        super("Supply Category Name should not be empty");
    }
}
