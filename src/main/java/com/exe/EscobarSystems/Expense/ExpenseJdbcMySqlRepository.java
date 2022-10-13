package com.exe.EscobarSystems.Expense;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository("expense_jdbc_mysql")
public class ExpenseJdbcMySqlRepository implements ExpenseDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExpenseJdbcMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private ExpenseTransactionDto mapExpenseTransactionResult(final ResultSet rs) throws SQLException {
        ExpenseTransactionDto expenseTransactionDto = new ExpenseTransactionDto();
        expenseTransactionDto.setTransactionId(rs.getLong("transaction_id"));
        expenseTransactionDto.setTransactionDate(rs.getObject("transaction_date", LocalDateTime.class));
        expenseTransactionDto.setTransactionSupplyQuantity(rs.getBigDecimal("transaction_supply_quantity"));
//        expenseTransactionDto.setTransactionType(rs.getString(rs.getString("transaction_type")));
        expenseTransactionDto.setSupplyName(rs.getString("supply_name"));
        expenseTransactionDto.setSupplierName(rs.getString("supplier_name"));
        expenseTransactionDto.setEmployeeName(rs.getString("employee_name"));

        BigDecimal pricePerUnit = rs.getObject("price_per_unit", BigDecimal.class);
        BigDecimal quantity = rs.getObject("price_per_unit", BigDecimal.class);
        expenseTransactionDto.setExpenseCost(pricePerUnit.multiply(quantity));

        return expenseTransactionDto;
    }

    public List<ExpenseTransactionDto> getAllTransactionExpensesByMonth(){
        String query = """
                 SELECT *, CONCAT(employee_last_name, ', ', employee_first_name) AS employee_name
                    FROM transaction
                    INNER JOIN supply ON transaction.supply_id = supply.supply_id
                    INNER JOIN supplier ON transaction.supplier_id = supplier.supplier_id
                    INNER JOIN employee ON transaction.transact_by = employee.employee_id
                """;

        List<ExpenseTransactionDto> transactionExpenses = jdbcTemplate
                .query(query, (rs, rowNum) -> mapExpenseTransactionResult(rs));

        return transactionExpenses;
    }

    private ExpenseDto mapExpenseResult(final ResultSet rs) throws SQLException {
        ExpenseDto expenseDto = new ExpenseDto();
        expenseDto.setExpenseId(rs.getLong("expense_id"));
        expenseDto.setExpenseCategoryName(rs.getString("expense_category_name"));
        expenseDto.setExpenseDescription(rs.getString("expense_description"));
        expenseDto.setExpenseDate(rs.getObject("expense_date", LocalDateTime.class));
        expenseDto.setExpenseCost(rs.getBigDecimal("expense_cost"));

        return expenseDto;
    }

    public List<ExpenseDto> getAllExpensesByMonth(){
        String query = """
                 SELECT * FROM expense
                    INNER JOIN expense_category ON expense.expense_category_id = expense_category.expense_category_id;
                """;
        List<ExpenseDto> expenses = jdbcTemplate
                .query(query, (rs, rowNum) -> mapExpenseResult(rs));

        return expenses;
    }

    private ExpenseBarGraphDto mapExpenseBarGraphDateResult(final ResultSet rs) throws SQLException {
        ExpenseBarGraphDto expenseBarGraphDto = new ExpenseBarGraphDto();
        expenseBarGraphDto.setExpenseMonth(rs.getString("expense_date"));
        expenseBarGraphDto.setMonthlyExpenses(rs.getDouble("expense"));
        expenseBarGraphDto.setMonthlyIncome(rs.getDouble("income"));
        return expenseBarGraphDto;
    }

    public List<ExpenseBarGraphDto> getGraphDataByMonth(FromToDate fromToDate){

        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        System.out.println("fromDate: " + fromDate);
        System.out.println("toDate: " + toDate);

        String query = """
                SELECT expense_date, SUM(expense_cost) as expense, income
                FROM (
                	SELECT DATE_FORMAT(transaction_date, '%M %Y') AS expense_date, SUM(price_per_unit * transaction_supply_quantity) AS expense_cost
                		FROM transaction WHERE transaction_type = 'STOCK_IN' AND transaction_date BETWEEN ? AND ?
                		GROUP BY expense_date
                 UNION
                	SELECT DATE_FORMAT(expense_date, '%M %Y') AS expense_date, SUM(expense_cost) AS expense_cost FROM expense
                	WHERE expense_date BETWEEN ? AND ?
                		GROUP BY expense_date
                ) AS expense
                 INNER JOIN(
                	SELECT DATE_FORMAT(order_time, '%M %Y') AS income_date, SUM(total_cost) AS income
                		FROM customer_food_order
                		INNER JOIN customer_order ON customer_food_order.order_id = customer_order.order_id
                		WHERE order_time BETWEEN ? AND ?
                		GROUP BY income_date) AS income ON expense_date = income_date
                GROUP BY expense_date;
                """;

        List<ExpenseBarGraphDto> graphData = jdbcTemplate.query(query, (rs, rowNum) -> mapExpenseBarGraphDateResult(rs), fromDate, toDate, fromDate, toDate, fromDate, toDate);

        return graphData;
    }

    public void addExpense(Long expenseCategoryId,
                           String expenseDescription,
                           LocalDateTime expenseDate,
                           BigDecimal expenseCost){

        String query = """
                INSERT INTO expense(expense_category_id, expense_description, expense_date, expense_cost)
                VALUES (?, ?, ?, ?)
                """;

        jdbcTemplate.update(query, expenseCategoryId, expenseDescription, expenseDate, expenseCost);
    }

    public void deleteExpense(Long expenseId){

        String query = """
                DELETE FROM expense WHERE expense_id = ?
                """;

        jdbcTemplate.update(query, expenseId);
    }

    private IncomeTablesBarGraphDto mapIncomeTablesBarGraphDto(final ResultSet rs) throws SQLException {
        IncomeTablesBarGraphDto incomeTablesBarGraphDto = new IncomeTablesBarGraphDto();
        incomeTablesBarGraphDto.setTableNumber((rs.getString("table_number")));
        incomeTablesBarGraphDto.setTotalCount((rs.getInt("table_count")));

        return incomeTablesBarGraphDto;
    }

    public List<IncomeTablesBarGraphDto> getIncomeTablesBarGraphByMonth(FromToDate fromToDate){
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        String query = """
                SELECT CONCAT('Table ', table_number) AS table_number, COUNT(table_number) AS table_count
                FROM customer_order
                WHERE payment_status='PAID' AND order_time BETWEEN ? AND ? 
                GROUP BY table_number
                """;

        List<IncomeTablesBarGraphDto> incomeTablesBarGraph = jdbcTemplate.query(query, (rs, rowNum) -> mapIncomeTablesBarGraphDto(rs), fromDate, toDate);

        return incomeTablesBarGraph;
    }

    private IncomeLineGraphDto mapIncomeLineGraphDateResult(final ResultSet rs) throws SQLException {
        IncomeLineGraphDto incomeLineGraphDto = new IncomeLineGraphDto();
        incomeLineGraphDto.setIncomeHour(rs.getString("income_hour"));
        incomeLineGraphDto.setHourlyIncome(rs.getDouble("hourly_income"));
        incomeLineGraphDto.setHourlyOrders(rs.getInt("hourly_orders"));
        return incomeLineGraphDto;
    }

    public List<IncomeLineGraphDto> getIncomeLineGraphByMonth(FromToDate fromToDate){
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        String query = """
                SELECT SUM(total_cost) AS hourly_income, DATE_FORMAT(order_time, '%h:00 %p' ) AS income_hour, COUNT(order_id) AS hourly_orders
                FROM customer_order
                WHERE payment_status="PAID" AND order_time BETWEEN ? AND ?
                GROUP BY HOUR(order_time);
                """;

        List<IncomeLineGraphDto> incomeLineGraphData = jdbcTemplate.query(query, (rs, rowNum) -> mapIncomeLineGraphDateResult(rs), fromDate, toDate);
        return incomeLineGraphData;
    }

    private ServingTypeGraphDto mapServingTypeGraphDto(final ResultSet rs) throws SQLException {
        ServingTypeGraphDto servingTypeGraphDto = new ServingTypeGraphDto();
        servingTypeGraphDto.setServingType(rs.getString("serving_type"));
        servingTypeGraphDto.setServingTypeQuantity(rs.getInt("serving_type_quantity"));
        return servingTypeGraphDto;
    }

    public List<ServingTypeGraphDto> getServingTypeGraph(FromToDate fromToDate){
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        String query = """
                SELECT  serving_type, COUNT(serving_type) as serving_type_quantity
                FROM customer_order
                WHERE payment_status="PAID" AND order_time BETWEEN ? AND ?
                GROUP BY serving_type
                """;

        List<ServingTypeGraphDto> servingTypeGraph = jdbcTemplate.query(query, (rs, rowNum) -> mapServingTypeGraphDto(rs), fromDate, toDate);
        return servingTypeGraph;
    }

    private IncomeDto mapIncomeResult(final ResultSet rs) throws SQLException {
        IncomeDto incomeDto = new IncomeDto();
        incomeDto.setIncomeDate(rs.getString("income_date"));
        incomeDto.setDailyIncome(rs.getBigDecimal("daily_income"));

        return incomeDto;
    }

    public List<IncomeDto> getAllIncomeByMonth(){

        String query = """
                SELECT DATE_FORMAT(order_time, '%M %d, %Y') AS income_date, SUM(total_cost) AS daily_income
                FROM customer_food_order
                    INNER JOIN customer_order ON customer_food_order.order_id = customer_order.order_id
                GROUP BY income_date;
                """;

        List<IncomeDto> income = jdbcTemplate.query(query, (rs, rowNum) -> mapIncomeResult(rs));

        return income;
    }

    private OrdersServedDto mapOrdersServedDto(final ResultSet rs) throws SQLException {
        OrdersServedDto ordersServed = new OrdersServedDto();
        ordersServed.setOrderID(rs.getInt("order_id"));
        ordersServed.setOrderTime(rs.getString("order_time"));
        ordersServed.setServingType(rs.getString("serving_type"));
        ordersServed.setTableNumber(rs.getString("table_number"));
        ordersServed.setPaymentStatus(rs.getString("payment_status"));
        ordersServed.setEmployeeName(rs.getString("employee_name"));
        return ordersServed;
    }

    public List<OrdersServedDto> getOrdersServed(FromToDate fromToDate){
        LocalDateTime fromDate = fromToDate.getFromDate();
        LocalDateTime toDate = fromToDate.getToDate();

        String query = """
                SELECT order_id, order_time, serving_type, CONCAT('Table ', table_number) as table_number, payment_status, CONCAT(employee.employee_last_name, ', ', employee.employee_first_name) AS employee_name
                FROM customer_order
                	INNER JOIN employee ON employee.employee_id = customer_order.employee_id
                WHERE order_time BETWEEN  ? AND ?
                """;
        List<OrdersServedDto> ordersServed = jdbcTemplate.query(query, (rs, rowNum) -> mapOrdersServedDto(rs), fromDate, toDate);
        return ordersServed;
    }






}
