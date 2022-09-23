package com.exe.EscobarSystems.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("account_mysql")
public interface AccountMySqlRepository extends AccountDao, JpaRepository<Account, Long> {

    @Query(value= "SELECT * FROM #{#entityName} WHERE is_active = true",
        nativeQuery = true)
    List<Account> getAllActiveAccounts();

    @Query(value= "SELECT * FROM #{#entityName} WHERE is_active = false",
            nativeQuery = true)
    List<Account> getAllInactiveAccounts();

    @Modifying
    @Query(value = "INSERT INTO #{#entityName}" +
            "(account_username, account_password, employee_id, access_inventory_management_system, " +
            "access_employee_management_system, access_income_and_expense_system, access_ordering_system, is_active) " +
            "VALUES (:accountUsername, :accountPassword, :employeeId, :accessInventoryManagementSystem, " +
            ":accessEmployeeManagementSystem, :accessIncomeAndExpenseSystem, :accessOrderingSystem, :isActive)",
            nativeQuery = true)
    void addAccount(@Param("accountUsername")String accountUsername,
                    @Param("accountPassword")String accountPassword,
                    @Param("employeeId")Long employeeId,
                    @Param("accessInventoryManagementSystem")Boolean accessInventoryManagementSystem,
                    @Param("accessEmployeeManagementSystem")Boolean accessEmployeeManagementSystem,
                    @Param("accessIncomeAndExpenseSystem")Boolean accessIncomeAndExpenseSystem,
                    @Param("accessOrderingSystem")Boolean accessOrderingSystem,
                    @Param("isActive")Boolean isActive);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=false WHERE account_username IN :accountUsernames",
            nativeQuery = true)
    void inactivateAccount(@Param("accountUsernames") List<String> accountUsernames);

    @Modifying
    @Query(value = "UPDATE #{#entityName} SET is_active=true WHERE account_username IN :accountUsernames",
            nativeQuery = true)
    void activateAccount(@Param("accountUsernames") List<String> accountUsernames);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE account_id = :accountId",
            nativeQuery = true)
    Optional<Account> getAccountById(@Param("accountId")Long accountId);

    @Query(value = "SELECT * FROM #{#entityName} " +
            "WHERE account_username = :accountUsername",
            nativeQuery = true)
    Optional<Account> getAccountByName(@Param("accountUsername")String accountUsername);
}
