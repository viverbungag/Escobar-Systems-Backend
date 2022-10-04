package com.exe.EscobarSystems.CustomerFoodOrder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("customerFoodOrder_mysql")
public interface CustomerFoodOrderMySqlRepository extends CustomerFoodOrderDao, JpaRepository<CustomerFoodOrder, Long> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE food_order_id = :foodOrderId AND order_id = :orderId",
            nativeQuery = true)
    Optional<CustomerFoodOrder> findCustomerFoodOrderByFoodOrderIdAndOrderId(@Param("foodOrderId")Long foodOrderId, @Param("orderId")Long orderId);
}
