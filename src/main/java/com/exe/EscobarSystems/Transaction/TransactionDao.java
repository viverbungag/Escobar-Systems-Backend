package com.exe.EscobarSystems.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionDao {

    Page<Transaction> getAllPagedTransactions(Pageable pageable, TransactionFiltersPaginationDto transactionFiltersPaginationDto);

    void insertTransaction(Long transactById,
                         LocalDateTime transactionDate,
                         Long supplierId,
                         Double quantity,
                         Long supplyId,
                         Double pricePerUnit,
                         LocalDateTime expiryDate,
                         String transactionType);

    Page<Transaction> getAllPagedExpiredTransactions(Pageable pageable);
}
