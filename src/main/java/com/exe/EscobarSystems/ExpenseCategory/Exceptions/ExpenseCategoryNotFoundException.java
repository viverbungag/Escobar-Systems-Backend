package com.exe.EscobarSystems.ExpenseCategory.Exceptions;

public class ExpenseCategoryNotFoundException extends RuntimeException {

    public ExpenseCategoryNotFoundException(Long id){
        super(String.format("Could not find Expense Category with id: %s", id));
    }

    public ExpenseCategoryNotFoundException(String name){
        super(String.format("Could not find Expense Category with name: %s", name));
    }
}
