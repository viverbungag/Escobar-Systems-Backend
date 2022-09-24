package com.exe.EscobarSystems.Expense.Exceptions;

public class ExpenseCostIsNegativeException extends RuntimeException{

    public ExpenseCostIsNegativeException(){
        super("The expense cost should not be negative");
    }
}
