package com.exe.EscobarSystems.Expense;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FromToDate {


    private LocalDateTime fromDate;
    private LocalDateTime toDate;

}
