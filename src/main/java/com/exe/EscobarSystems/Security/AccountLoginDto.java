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

    private String accountUsername;
    private String accountPassword;
    private String employeeName;
}
