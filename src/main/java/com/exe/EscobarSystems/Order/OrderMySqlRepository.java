package com.exe.EscobarSystems.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("order_mysql")
public interface OrderMySqlRepository extends  OrderDao, JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM #{#entityName}",
            countQuery = "SELECT * FROM #{#entityName}",
            nativeQuery = true)
    Page<Order> getAllPagedOrders(Pageable pageable);

    @Query(value = "INSERT INTO #{#entityName}(employee_id, order_time, payment, total_cost, discount) " +
            "VALUES (:employeeId, :orderTime, :payment, :totalCost, :discount);",
            nativeQuery = true)
    @Modifying
    Long insertOrder(@Param("employeeId")Long employeeId,
                     @Param("orderTime")LocalDateTime orderTime,
                     @Param("payment")BigDecimal payment,
                     @Param("totalCost")BigDecimal totalCost,
                     @Param("discount")BigDecimal discount);

    @Query(value = "DELETE FROM #{#entityName} " +
            "WHERE order_id = :orderId",
            nativeQuery = true)
    @Modifying
    void removeOrder(@Param("orderId")Long orderId);

    @Query(value = "INSERT INTO customer_food_order(food_order_id, order_id) " +
            "VALUES (:foodOrderId, :orderId)",
            nativeQuery = true)
    @Modifying
    void insertCustomerFoodOrder(@Param("foodOrderId")Long foodOrderId,
                                 @Param("orderId")Long orderId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE order_time = :orderTime",
            nativeQuery = true)
    Optional<Order> getOrderByOrderTime(@Param("orderTime")LocalDateTime orderTime);

    @Query(value = "SELECT * FROM #{#entityName} WHERE order_id = :orderId",
            nativeQuery = true)
    Optional<Order> getOrderByOrderId(@Param("orderId")Long orderId);

    @Query(value = "SELECT * FROM #{#entityName} WHERE DATE_SUB(NOW(), INTERVAL 8 HOUR) < order_time",
            nativeQuery = true)
    List<Order> getAllOrdersToday();

}
