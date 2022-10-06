package com.exe.EscobarSystems.Expense;

import com.exe.EscobarSystems.Expense.Exceptions.ExpenseCostIsNegativeException;
import com.exe.EscobarSystems.Expense.Exceptions.ExpenseNotFoundException;
import com.exe.EscobarSystems.ExpenseCategory.Exceptions.ExpenseCategoryNotFoundException;
import com.exe.EscobarSystems.ExpenseCategory.ExpenseCategory;
import com.exe.EscobarSystems.ExpenseCategory.ExpenseCategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExpenseService {

    @Autowired
    @Qualifier("expense_jdbc_mysql")
    ExpenseDao expenseJdbcRepository;

    @Autowired
    ExpenseMySqlRepository expenseRepository;

    @Autowired
    @Qualifier("expenseCategory_mysql")
    ExpenseCategoryDao expenseCategoryRepository;

    public List<ExpenseTransactionDto> getAllExpensesTransactionByMonth(){
        return expenseJdbcRepository.getAllTransactionExpensesByMonth();
    }

    public List<ExpenseDto> getAllExpensesByMonth(){
        return expenseJdbcRepository.getAllExpensesByMonth();
    }

    public List<ExpenseBarGraphDto> getBarGraphDataByMonth(FromToDate fromToDate) {
        return expenseJdbcRepository.getGraphDataByMonth(fromToDate);
    }

    public List<ExpenseDonutGraphDto> getDonutGraphDataByMonth(FromToDate fromToDate){
        List<ExpenseBarGraphDto> graphDataByMonth = expenseJdbcRepository.getGraphDataByMonth(fromToDate);
        Double totalExpense = graphDataByMonth.stream().reduce(0.0, (sum, data) -> sum + data.getMonthlyExpenses(), Double::sum);
        Double totalIncome = graphDataByMonth.stream().reduce(0.0, (sum, data) -> sum + data.getMonthlyIncome(), Double::sum);

        List<ExpenseDonutGraphDto> expenseDonutGraphDtoList = new ArrayList<>();

        ExpenseDonutGraphDto expenseDonutGraphDtoExpense = new ExpenseDonutGraphDto();
        expenseDonutGraphDtoExpense.setDonutLabel("Expense");
        expenseDonutGraphDtoExpense.setDonutData(totalExpense);


        ExpenseDonutGraphDto expenseDonutGraphDtoIncome = new ExpenseDonutGraphDto();
        expenseDonutGraphDtoIncome.setDonutLabel("Income");
        expenseDonutGraphDtoIncome.setDonutData(totalIncome);


        expenseDonutGraphDtoList.add(expenseDonutGraphDtoExpense);
        expenseDonutGraphDtoList.add(expenseDonutGraphDtoIncome);

        return expenseDonutGraphDtoList;
    }

    public List<IncomeTablesBarGraphDto> getIncomeTablesBarGraphByMonth(FromToDate fromToDate){
        return expenseJdbcRepository.getIncomeTablesBarGraphByMonth(fromToDate);
    }
    public List<IncomeLineGraphDto> getLineGraphDataByMonth(FromToDate fromToDate){
        return expenseJdbcRepository.getIncomeLineGraphByMonth(fromToDate);
    }

    public List<ServingTypeGraphDto> getServingTypeGraph(FromToDate fromToDate){
        return expenseJdbcRepository.getServingTypeGraph(fromToDate);
    }

    public void addExpense(ExpenseDto expenseDto){

        String expenseDescription = expenseDto.getExpenseDescription();
        LocalDateTime expenseDate = expenseDto.getExpenseDate();
        BigDecimal expenseCost = expenseDto.getExpenseCost();
        String expenseCategoryName = expenseDto.getExpenseCategoryName();

        if (expenseCost.compareTo(new BigDecimal(0)) < 0){
            throw new ExpenseCostIsNegativeException();
        }

        ExpenseCategory expenseCategory = expenseCategoryRepository
                .getExpenseCategoryByName(expenseCategoryName)
                .orElseThrow(() -> new ExpenseCategoryNotFoundException(expenseCategoryName));

        expenseJdbcRepository.addExpense(expenseCategory.getExpenseCategoryId(), expenseDescription, expenseDate, expenseCost);
    }

    public void deleteExpense(Long expenseId){
        expenseJdbcRepository.deleteExpense(expenseId);
    }

    public void updateExpense(Long expenseId, ExpenseDto expenseDto){

        Expense expenseToBeUpdated = expenseRepository.getExpenseById(expenseId)
                .orElseThrow(() -> new ExpenseNotFoundException(expenseId));

        String expenseDescription = expenseDto.getExpenseDescription();
        LocalDateTime expenseDate = expenseDto.getExpenseDate();
        BigDecimal expenseCost = expenseDto.getExpenseCost();
        String expenseCategoryName = expenseDto.getExpenseCategoryName();

        ExpenseCategory expenseCategory = expenseCategoryRepository
                .getExpenseCategoryByName(expenseCategoryName)
                .orElseThrow(() -> new ExpenseCategoryNotFoundException(expenseCategoryName));

        if (expenseCost.compareTo(new BigDecimal(0)) < 0){
            throw new ExpenseCostIsNegativeException();
        }

        expenseToBeUpdated.setExpenseDescription(expenseDescription);
        expenseToBeUpdated.setExpenseDate(expenseDate);
        expenseToBeUpdated.setExpenseCost(expenseCost);
        expenseToBeUpdated.setExpenseCategory(expenseCategory);

    }

    public List<IncomeDto> getAllIncomeByMonth(){

        return expenseJdbcRepository.getAllIncomeByMonth();
    }

    public List<OrdersServedDto> getOrdersServed(FromToDate fromToDate) {

        return expenseJdbcRepository.getOrdersServed(fromToDate);
    }
}
