package com.exe.EscobarSystems.Transaction.Exceptions;

public class TransactionDateIsNullException extends RuntimeException{

    public TransactionDateIsNullException(){
        super("Transaction Date field should not be empty");
    }
}
