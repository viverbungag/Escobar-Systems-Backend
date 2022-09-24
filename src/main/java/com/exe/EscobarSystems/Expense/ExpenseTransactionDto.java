package com.exe.EscobarSystems.Expense;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExpenseTransactionDto {

    private Long transactionId;
    private LocalDateTime transactionDate;
    private String supplyName;
    private BigDecimal expenseCost;
}
