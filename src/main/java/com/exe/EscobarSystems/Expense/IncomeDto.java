package com.exe.EscobarSystems.Expense;

import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IncomeDto {

    private String incomeDate;
    private BigDecimal dailyIncome;
}
