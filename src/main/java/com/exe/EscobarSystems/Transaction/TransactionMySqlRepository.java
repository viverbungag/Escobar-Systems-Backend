package com.exe.EscobarSystems.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("transaction_mysql")
public interface TransactionMySqlRepository extends JpaRepository<Transaction, Long> {

//    @Query(value = "SELECT * FROM #{#entityName} AS transaction" +
//            " INNER JOIN supplier AS supplier ON transaction.supplier_id = supplier.supplier_id" +
//            " INNER JOIN supply AS supply ON transaction.supply_id = supply.supply_id" +
//            " INNER JOIN employee AS employee ON transaction.transact_by = employee.employee_id" +
//            " WHERE supply.supply_name IN :supplyFilter",
//            nativeQuery = true)
//    Page<Transaction> getAllPagedTransactions(Pageable pageable, TransactionFiltersPaginationDto transactionFiltersPaginationDto);

    @Modifying
    @Query(value = "INSERT INTO #{#entityName} " +
            "(transact_by, transaction_date, supplier_id, transaction_supply_quantity, supply_id, price_per_unit, expiry_date, transaction_type) " +
            "VALUES (:transactById, :transactionDate, :supplierId, :quantity, :supplyId, :pricePerUnit, :expiryDate, :transactionType)",
            nativeQuery = true)
    void insertTransaction(@Param("transactById") Long transactById,
                           @Param("transactionDate") LocalDateTime transactionDate,
                           @Param("supplierId") Long supplierId,
                           @Param("quantity") Double quantity,
                           @Param("supplyId") Long supplyId,
                           @Param("pricePerUnit") Double pricePerUnit,
                           @Param("expiryDate") LocalDateTime expiryDate,
                           @Param("transactionType") String transactionType);
}
