package com.exe.EscobarSystems.Order;

import com.exe.EscobarSystems.CustomerFoodOrder.CustomerFoodOrderDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {

    private Long orderId;
    private String employeeFullName;
    private LocalDateTime orderTime;
    private List<CustomerFoodOrderDto> customerFoodOrders;
    private BigDecimal payment;
    private BigDecimal discount;
    private BigDecimal totalCost;
}
