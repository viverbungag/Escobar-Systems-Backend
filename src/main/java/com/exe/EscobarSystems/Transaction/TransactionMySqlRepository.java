package com.exe.EscobarSystems.Transaction;

import com.exe.EscobarSystems.SupplyCategory.SupplyCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository("transaction_mysql")
public interface TransactionMySqlRepository extends JpaRepository<TransactionDedicatedToExpired, Long> {

    @Query(value = "SELECT * FROM transaction_dedicated_to_expired " +
            "WHERE transaction_id = :transactionId",
            nativeQuery = true)
    Optional<TransactionDedicatedToExpired> findTransactionById(@Param("transactionId") Long transactionId);

    @Query(value = "DELETE FROM transaction_dedicated_to_expired " +
            "WHERE transaction_id = :transactionId",
            nativeQuery = true)
    @Modifying
    void deleteTransactionById(@Param("transactionId") Long transactionId);
}
