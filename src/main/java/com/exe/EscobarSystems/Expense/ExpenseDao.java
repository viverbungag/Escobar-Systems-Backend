package com.exe.EscobarSystems.Expense;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseDao {

    List<ExpenseTransactionDto> getAllTransactionExpensesByMonth();

    List<ExpenseDto> getAllExpensesByMonth();

    List<ExpenseBarGraphDto> getGraphDataByMonth(FromToDate fromToDate);

    void addExpense(Long expenseCategoryId,
                           String expenseDescription,
                           LocalDateTime expenseDate,
                           BigDecimal expenseCost);

    void deleteExpense(Long expenseId);

    List<IncomeTablesBarGraphDto> getIncomeTablesBarGraphByMonth(FromToDate fromToDate);

    List<IncomeLineGraphDto> getIncomeLineGraphByMonth(FromToDate fromToDate);

    List<IncomeDto> getAllIncomeByMonth();

    List<OrdersServedDto> getOrdersServed(FromToDate fromToDate);

    List<ServingTypeGraphDto> getServingTypeGraph(FromToDate fromToDate);
}
