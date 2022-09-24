package com.exe.EscobarSystems.Expense;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseBarGraphDto {

    private String expenseMonth;
    private Double monthlyIncome;
    private Double monthlyExpenses;
}
