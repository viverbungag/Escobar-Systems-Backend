package com.exe.EscobarSystems.CustomerFoodOrder;


import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerFoodOrderDao {

    Optional<CustomerFoodOrder> findCustomerFoodOrderByFoodOrderIdAndOrderId(Long foodOrderId, Long orderId);
}
