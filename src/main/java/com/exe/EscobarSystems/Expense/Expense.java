package com.exe.EscobarSystems.Expense;

import com.exe.EscobarSystems.ExpenseCategory.ExpenseCategory;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "expense_category_id")
    private ExpenseCategory expenseCategory;

    @NonNull
    @Column(name = "expense_description")
    private String expenseDescription;

    @NonNull
    @Column(name = "expense_date")
    private LocalDateTime expenseDate;

    @NonNull
    @Column(name = "expense_cost")
    private BigDecimal expenseCost;

    //TODO: Edit the database of the expense
}
