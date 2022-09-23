package com.exe.EscobarSystems.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class TransactionPrintDetailsDto {

    List<TransactionDto> transactions;
    String accountFullName;
}
