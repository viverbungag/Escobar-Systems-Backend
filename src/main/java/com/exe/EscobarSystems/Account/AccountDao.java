package com.exe.EscobarSystems.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDao {

    List<Account> getAllActiveAccounts();

    List<Account> getAllInactiveAccounts();

    void addAccount(String accountUsername,
                    String accountPassword,
                    Long employeeId,
                    Boolean accessInventoryManagementSystem,
                    Boolean accessEmployeeManagementSystem,
                    Boolean accessIncomeAndExpenseSystem,
                    Boolean accessOrderingSystem,
                    Boolean isActive);

    void inactivateAccount(List<String> accountUsernames);

    void activateAccount(List<String> accountUsernames);

    Optional<Account> getAccountById(Long accountId);

    Optional<Account> getAccountByName(String accountName);
}
