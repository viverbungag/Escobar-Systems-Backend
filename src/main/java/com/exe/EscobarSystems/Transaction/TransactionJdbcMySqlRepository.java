package com.exe.EscobarSystems.Transaction;

import com.exe.EscobarSystems.Employee.Employee;
import com.exe.EscobarSystems.EmployeePosition.EmployeePosition;
import com.exe.EscobarSystems.EmployeeType.EmployeeType;
import com.exe.EscobarSystems.Supplier.Supplier;
import com.exe.EscobarSystems.Supply.Supply;
import com.exe.EscobarSystems.SupplyCategory.SupplyCategory;
import com.exe.EscobarSystems.UnitOfMeasurement.UnitOfMeasurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository("transaction_jdbc_mysql")
public class TransactionJdbcMySqlRepository implements TransactionDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionJdbcMySqlRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Employee setUpEmployee(final ResultSet rs) throws SQLException{
        Long employeeId = rs.getLong("transact_by");
        String firstName = rs.getString("employee_first_name");
        String lastName = rs.getString("employee_last_name");
        String employeeAddress = rs.getString("employee_address");
        String employeeContactNumber = rs.getString("employee_contact_number");
        LocalDateTime dateEmployed = rs.getObject("date_employed", LocalDateTime.class);
        Boolean isActive = rs.getBoolean("is_active");


        return new Employee(employeeId,
                firstName,
                lastName,
                employeeAddress,
                employeeContactNumber,
                dateEmployed,
                new EmployeePosition(),
                new EmployeeType(),
                new Employee(),
                isActive);
    }

    private Supplier setUpSupplier(final ResultSet rs) throws SQLException{
        Long supplierId = rs.getLong("supplier_id");
        String supplierName = rs.getString("supplier_name");
        String supplierAddress = rs.getString("supplier_address");
        String supplierContactNumber = rs.getString("supplier_contact_number");
        String supplierContactPerson = rs.getString("supplier_contact_person");
        Boolean isActive = rs.getBoolean("is_active");

        return new Supplier(supplierId, supplierName, supplierAddress, supplierContactNumber, supplierContactPerson, isActive);
    }

    private Supplier setUpSupplier2(final ResultSet rs) throws SQLException{
        Long supplierId = rs.getLong("supplier2.supplier_id");
        String supplierName = rs.getString("supplier2.supplier_name");
        String supplierAddress = rs.getString("supplier2.supplier_address");
        String supplierContactNumber = rs.getString("supplier2.supplier_contact_number");
        String supplierContactPerson = rs.getString("supplier2.supplier_contact_person");
        Boolean isActive = rs.getBoolean("supplier2.is_active");

        return new Supplier(supplierId, supplierName, supplierAddress, supplierContactNumber, supplierContactPerson, isActive);
    }

    private UnitOfMeasurement setUpUnitOfMeasurement(final ResultSet rs) throws SQLException{
        Long unitOfMeasurementId = rs.getLong("unit_of_measurement_id");
        String unitOfMeasurementName = rs.getString("unit_of_measurement_name");
        String unitOfMeasurementAbbreviation = rs.getString("unit_of_measurement_abbreviation");
        Boolean isActive = rs.getBoolean("unit_of_measurement.is_active");

        return new UnitOfMeasurement(unitOfMeasurementId, unitOfMeasurementName, unitOfMeasurementAbbreviation, isActive);
    }

    private SupplyCategory setUpSupplyCategory(final ResultSet rs) throws SQLException{
        Long supplyCategoryId = rs.getLong("supply_category_id");
        String supplyCategoryName= rs.getString("supply_category_name");
        Boolean isActive = rs.getBoolean("supply_category.is_active");

        return new SupplyCategory(supplyCategoryId, supplyCategoryName, isActive);
    }

    private Supply setUpSupply(final ResultSet rs) throws SQLException{
        Long supplyId = rs.getLong("supply_id");
        String supplyName = rs.getString("supply_name");
        Double supplyQuantity = rs.getDouble("supply_quantity");
        Double minimumQuantity = rs.getDouble("minimum_quantity");
        Supplier supplier = setUpSupplier2(rs);
        UnitOfMeasurement unitOfMeasurement = setUpUnitOfMeasurement(rs);
        SupplyCategory supplyCategory = setUpSupplyCategory(rs);
        Boolean isActive = rs.getBoolean("supply.is_active");

        return new Supply(supplyId, supplyName, supplyQuantity, minimumQuantity, supplier, unitOfMeasurement, supplyCategory, isActive);
    }

    private TransactionType setupTransactionType(final ResultSet rs) throws SQLException{

        switch(rs.getString("transaction_type")){

            case "STOCK_IN":
                return TransactionType.STOCK_IN;

            default:
                return TransactionType.STOCK_OUT;
        }
    }



    private Transaction mapTransactionResult(final ResultSet rs) throws SQLException {
        Transaction transaction  = new Transaction();
        transaction.setTransactionId(rs.getLong("transaction_id"));
        transaction.setTransactBy(setUpEmployee(rs));
        transaction.setTransactionDate(rs.getObject("transaction_date", LocalDateTime.class));
        transaction.setSupplier(setUpSupplier(rs));
        transaction.setSupplyQuantity(rs.getDouble("transaction_supply_quantity"));
        transaction.setSupply(setUpSupply(rs));
        transaction.setPricePerUnit(rs.getDouble("transaction_supply_quantity"));
        transaction.setExpiryDate(rs.getObject("expiry_date", LocalDateTime.class));
        transaction.setTransactionType(setupTransactionType(rs));
        return transaction;
    }

    private int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM transaction", Integer.class);
    }

    private String constructFilterQuery (TransactionFiltersPaginationDto transactionFiltersPaginationDto){
        List<String> queryList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<String> supplyFilter = transactionFiltersPaginationDto.getSupplyFilter();
        List<String> unitOfMeasurementFilter = transactionFiltersPaginationDto.getUnitOfMeasurementFilter();
        List<String> supplierFilter = transactionFiltersPaginationDto.getSupplierFilter();
        String transactionDateStart = transactionFiltersPaginationDto.getTransactionDateStart().format(formatter);
        String transactionDateEnd = transactionFiltersPaginationDto.getTransactionDateEnd().format(formatter);
        List<TransactionType> transactionTypeFilter = transactionFiltersPaginationDto.getTransactionTypeFilter();
        Boolean isTransactionDateEnabled = transactionFiltersPaginationDto.getIsTransactionDateEnabled();


        if(supplyFilter.size() > 0){
            String supplies = String.join(", ",
                    supplyFilter
                            .stream()
                            .map((item) -> String.format("\"%s\"", item))
                            .collect(Collectors.toList()));
            queryList.add(String.format("supply.supply_name IN (%s)", supplies));
        }

        if (unitOfMeasurementFilter.size() > 0){
            String unitOfMeasurements = String.join(", ",
                    unitOfMeasurementFilter
                            .stream()
                            .map((item) -> String.format("\"%s\"", item))
                            .collect(Collectors.toList()));
            queryList.add(String.format("unit_of_measurement.unit_of_measurement_name IN (%s)", unitOfMeasurements));
        }

        if (supplierFilter.size() > 0){
            String suppliers = String.join(", ",
                    supplierFilter
                            .stream()
                            .map((item) -> String.format("\"%s\"", item))
                            .collect(Collectors.toList()));
            queryList.add(String.format("supplier.supplier_name IN (%s)", suppliers));
        }

        if (transactionTypeFilter.size() > 0){
            String transactionTypes = String.join(", ",
                    transactionTypeFilter
                            .stream()
                            .map((item) -> String.format("\"%s\"", item.name()))
                            .collect(Collectors.toList()));
            queryList.add(String.format("transaction.transaction_type IN (%s)", transactionTypes));
        }

        if (isTransactionDateEnabled){
            queryList.add(String.format("((transaction.transaction_date BETWEEN '%s' AND '%s') OR transaction_date = '%s' OR transaction_date = '%s')",
                    transactionDateStart, transactionDateEnd, transactionDateStart, transactionDateEnd));
        }

        if (queryList.size() > 0){
            return String.format("WHERE %s",String.join(" AND ", queryList));
        }


        return "";
    }

    @Override
    public Page<Transaction> getAllPagedTransactions(Pageable pageable, TransactionFiltersPaginationDto transactionFiltersPaginationDto){
        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("transaction_id");
        String queryFilter = constructFilterQuery(transactionFiltersPaginationDto);

        String query = "SELECT * FROM transaction AS transaction " +
                "INNER JOIN employee AS employee ON transaction.transact_by = employee.employee_id " +
                "INNER JOIN supplier AS supplier ON transaction.supplier_id = supplier.supplier_id " +
                "INNER JOIN supply AS supply ON transaction.supply_id = supply.supply_id " +
                "INNER JOIN unit_of_measurement AS unit_of_measurement ON supply.unit_of_measurement_id = unit_of_measurement.unit_of_measurement_id " +
                "INNER JOIN supplier AS supplier2 ON supply.supplier_id = supplier2.supplier_id " +
                "INNER JOIN supply_category AS supply_category ON supply.supply_category_id = supply_category.supply_category_id " +
                queryFilter + " " +
                "ORDER BY " + order.getProperty() + " "
                + order.getDirection().name() + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();

        List<Transaction> transactions = jdbcTemplate.query(query, (rs, rowNum) -> mapTransactionResult(rs));


        return new PageImpl<Transaction>(transactions, pageable, count());
    };

    @Override
    public void insertTransaction(Long transactById,
                           LocalDateTime transactionDate,
                           Long supplierId,
                           Double quantity,
                           Long supplyId,
                           Double pricePerUnit,
                           LocalDateTime expiryDate,
                           String transactionType){

        if (transactionType == "STOCK_IN"){
            String query = """
                INSERT INTO transaction_dedicated_to_expired(transact_by, transaction_date, supplier_id, transaction_supply_quantity, supply_id, price_per_unit, expiry_date, transaction_type)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;
            jdbcTemplate.update(query, transactById, transactionDate, supplierId, quantity, supplyId, pricePerUnit, expiryDate, transactionType);
        }

        String query = """
                INSERT INTO transaction(transact_by, transaction_date, supplier_id, transaction_supply_quantity, supply_id, price_per_unit, expiry_date, transaction_type)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(query, transactById, transactionDate, supplierId, quantity, supplyId, pricePerUnit, expiryDate, transactionType);
    };

    @Override
    public Page<Transaction> getAllPagedExpiredTransactions(Pageable pageable){

        Sort.Order order = !pageable.getSort().isEmpty() ? pageable.getSort().toList().get(0) : Sort.Order.by("transaction_id");

        String query = "SELECT * FROM transaction_dedicated_to_expired AS transaction " +
                "INNER JOIN employee AS employee ON transaction.transact_by = employee.employee_id " +
                "INNER JOIN supplier AS supplier ON transaction.supplier_id = supplier.supplier_id " +
                "INNER JOIN supply AS supply ON transaction.supply_id = supply.supply_id " +
                "INNER JOIN unit_of_measurement AS unit_of_measurement ON supply.unit_of_measurement_id = unit_of_measurement.unit_of_measurement_id " +
                "INNER JOIN supplier AS supplier2 ON supply.supplier_id = supplier2.supplier_id " +
                "INNER JOIN supply_category AS supply_category ON supply.supply_category_id = supply_category.supply_category_id " +
                "WHERE expiry_date < NOW() AND transaction_type='STOCK_IN'" +
                "ORDER BY " + order.getProperty() + " "
                + order.getDirection().name() + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();

        List<Transaction> transactions = jdbcTemplate.query(query, (rs, rowNum) -> mapTransactionResult(rs));

        return new PageImpl<>(transactions, pageable, count());
    }
}
