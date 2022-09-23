package com.exe.EscobarSystems.Account;

import com.exe.EscobarSystems.Employee.Employee;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @NonNull
    @Column(name = "account_username")
    private String accountUsername;

    @NonNull
    @Column(name = "account_password")
    private String accountPassword;

    @NonNull
    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @NonNull
    @Column(name = "access_inventory_management_system")
    private Boolean accessInventoryManagementSystem;

    @NonNull
    @Column(name = "access_employee_management_system")
    private Boolean accessEmployeeManagementSystem;

    @NonNull
    @Column(name = "access_income_and_expense_system")
    private Boolean accessIncomeAndExpenseSystem;

    @NonNull
    @Column(name = "access_ordering_system")
    private Boolean accessOrderingSystem;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;
}
