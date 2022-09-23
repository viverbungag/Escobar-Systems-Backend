package com.exe.EscobarSystems.Transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@AllArgsConstructor
@Getter
@Setter
@ToString
public class TransactionDto {

    private Long transactionId;
    private String transactByName;
    private LocalDateTime transactionDate;
    private String supplierName;
    private Double supplyQuantity;
    private String supplyName;
    private String unitOfMeasurementAbbreviation;
    private Double pricePerUnit;
    private LocalDateTime expiryDate;
    private TransactionType transactionType;
}
