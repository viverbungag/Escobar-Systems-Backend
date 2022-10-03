package com.exe.EscobarSystems.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("order_jdbc_mysql")
public class OrderJdbcMysqlRepository implements OrderDao{

    private final JdbcTemplate jdbcTemplate ;

    @Autowired
    public OrderJdbcMysqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<Order> getAllPagedOrders(Pageable pageable){
        return null;
    }

    private Long orderIdResult(final ResultSet rs) throws SQLException{
        return rs.getLong("LAST_INSERT_ID()");
    }

    @Override
    public Long insertOrder(Long employeeId,
                            LocalDateTime orderTime,
                            BigDecimal payment,
                            BigDecimal totalCost,
                            BigDecimal discount,
                            BigDecimal additionalPayment,
                            PaymentStatus paymentStatus,
                            ServingType servingType,
                            Integer tableNumber){
        String query = """
                INSERT INTO customer_order(employee_id, order_time, payment, total_cost, discount, payment_status, serving,type, table_number, additional_payment)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        jdbcTemplate.update(query, employeeId, orderTime, payment, totalCost, discount, paymentStatus, servingType, tableNumber, additionalPayment);

        String queryForGettingId = "SELECT LAST_INSERT_ID();";

        List<Long> id = jdbcTemplate.query(queryForGettingId, (rs, rowNum) -> orderIdResult(rs));

        return id.get(0);
    }

    public void insertCustomerFoodOrder(Long foodOrderId, Long orderId){

    }

    public Optional<Order> getOrderByOrderTime(LocalDateTime orderTime){
        return null;
    }

    public void removeOrder(Long orderId){

    }

    public Optional<Order> getOrderByOrderId(Long orderId){
        return null;
    }

    public List<Order> getAllOrdersToday(){
        return null;
    };


    public Page<Order> getAllPagedPaidOrders(Pageable pageable){
        return null;
    }

    public Page<Order> getAllPagedUnpaidOrders(Pageable pageable){
        return null;
    };

    public List<Order> getAllUnpaidOrders(){
        return null;
    };

}
