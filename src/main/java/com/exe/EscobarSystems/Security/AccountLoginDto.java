package com.exe.EscobarSystems.Security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountLoginDto {

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
