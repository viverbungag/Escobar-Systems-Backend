package com.exe.EscobarSystems.Order.Exceptions;

import java.time.LocalDateTime;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Long id){
        super(String.format("Could not find order with id: %s", id));
    }

    public OrderNotFoundException(LocalDateTime time){
        super(String.format("Could not find order with time: %s", time));
    }
}
