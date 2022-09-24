package com.exe.EscobarSystems.ExpenseCategory;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity(name = "expense_category")
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "expense_category_id")
    private Long expenseCategoryId;

    @NonNull
    @Column(name = "expense_category_name")
    private String expenseCategoryName;

    @NonNull
    @Column(name = "is_active")
    private Boolean isActive;
}
