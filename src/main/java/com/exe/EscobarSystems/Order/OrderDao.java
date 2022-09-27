package com.exe.EscobarSystems.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDao {

    Page<Order> getAllPagedOrders(Pageable pageable);

    Long insertOrder(Long employeeId, LocalDateTime orderTime, BigDecimal payment, BigDecimal totalCost, BigDecimal discount);

    void insertCustomerFoodOrder(Long foodOrderId, Long orderId);

    Optional<Order> getOrderByOrderTime(LocalDateTime orderTime);

    void removeOrder(Long orderId);

    Optional<Order> getOrderByOrderId(Long orderId);

    List<Order> getAllOrdersToday();

}
