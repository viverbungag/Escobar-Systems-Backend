package com.exe.EscobarSystems.CustomerFoodOrder.Exceptions;

public class CustomerFoodOrderNotFoundException extends RuntimeException{

     public CustomerFoodOrderNotFoundException(Long FoodOrderId, Long orderId){
         super(String.format("Could not find Customer Food Order with FoodOrderId: %s and OrderId: %s", FoodOrderId, orderId));
     }
}
