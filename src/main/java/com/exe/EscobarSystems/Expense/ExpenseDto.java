package com.exe.EscobarSystems.Expense;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseDto {

    private Long expenseId;
    private String expenseCategoryName;
    private String expenseDescription;
    private LocalDateTime expenseDate;
    private BigDecimal expenseCost;
}
