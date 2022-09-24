package com.exe.EscobarSystems.Order;

import com.exe.EscobarSystems.CustomerFoodOrder.CustomerFoodOrder;
import com.exe.EscobarSystems.Employee.Employee;
import lombok.*;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @NonNull
    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<CustomerFoodOrder> customerFoodOrders;

    @NonNull
    @Column(name = "payment")
    private BigDecimal payment;

    @NonNull
    @Column(name = "discount")
    private BigDecimal discount;

    @NonNull
    @Column(name = "total_cost")
    private BigDecimal totalCost;
}
