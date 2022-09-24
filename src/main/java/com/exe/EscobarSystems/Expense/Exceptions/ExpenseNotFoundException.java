package com.exe.EscobarSystems.Expense.Exceptions;

public class ExpenseNotFoundException extends RuntimeException{

    public ExpenseNotFoundException(Long id){
        super(String.format("Could not find Expense with id: %s", id));
    }

}
