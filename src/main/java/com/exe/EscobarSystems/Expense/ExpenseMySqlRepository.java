package com.exe.EscobarSystems.Expense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseMySqlRepository extends JpaRepository<Expense, Long> {

    @Query(value = "SELECT * FROM #{#entityName} WHERE expense_id = :expenseId",
            nativeQuery = true)
    Optional<Expense> getExpenseById(@Param("expenseId") Long expenseId);
}
