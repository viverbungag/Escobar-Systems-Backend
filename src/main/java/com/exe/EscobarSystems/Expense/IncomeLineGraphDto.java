package com.exe.EscobarSystems.Expense;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class IncomeLineGraphDto {
    private String incomeHour;
    private Double hourlyIncome;
}
