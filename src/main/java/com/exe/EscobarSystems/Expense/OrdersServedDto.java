package com.exe.EscobarSystems.Expense;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrdersServedDto {
    String orderTime;
    String servingType;
    Integer tableNumber;
    String paymentStatus;
    String employeeName;
}
