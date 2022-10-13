package com.exe.EscobarSystems.Expense;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrdersServedDto {
    Integer orderID;
    String orderTime;
    String servingType;
    String tableNumber;
    String paymentStatus;
    String employeeName;
}
