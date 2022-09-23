package com.exe.EscobarSystems.Account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccountDto {

    private Long accountId;
    private String accountUsername;
    private String accountPassword;
    private String employeeName;
    private Boolean accessInventoryManagementSystem;
    private Boolean accessEmployeeManagementSystem;
    private Boolean accessIncomeAndExpenseSystem;
    private Boolean accessOrderingSystem;
    private Boolean isActive;
}
