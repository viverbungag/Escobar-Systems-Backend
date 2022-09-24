package com.exe.EscobarSystems.ExpenseCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExpenseCategoryDto {

    private Long expenseCategoryId;
    private String expenseCategoryName;
    private Boolean isActive;
}
