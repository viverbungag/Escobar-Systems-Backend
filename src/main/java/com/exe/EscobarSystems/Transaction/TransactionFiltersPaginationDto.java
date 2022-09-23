package com.exe.EscobarSystems.Transaction;

import com.exe.EscobarSystems.Pagination.PaginationDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
public class TransactionFiltersPaginationDto extends PaginationDto {

    private List<String> supplyFilter;
    private List<String> unitOfMeasurementFilter;
    private List<String> supplierFilter;
    private LocalDateTime transactionDateStart;
    private LocalDateTime transactionDateEnd;
    private List<TransactionType> transactionTypeFilter;
    private Boolean isTransactionDateEnabled;

    public TransactionFiltersPaginationDto(int pageNo,
                                           int pageSize,
                                           String sortedBy,
                                           Boolean isAscending,
                                           List<String> supplyFilter,
                                           List<String> unitOfMeasurementFilter,
                                           List<String> supplierFilter,
                                           LocalDateTime transactionDateStart,
                                           LocalDateTime transactionDateEnd,
                                           List<TransactionType> transactionTypeFilter,
                                           Boolean isTransactionDateEnabled) {

        super(pageNo, pageSize, sortedBy, isAscending);

        this.supplyFilter = supplyFilter;
        this.unitOfMeasurementFilter = unitOfMeasurementFilter;
        this.supplierFilter = supplierFilter;
        this.transactionDateStart = transactionDateStart;
        this.transactionDateEnd = transactionDateEnd;
        this.transactionTypeFilter = transactionTypeFilter;
        this.isTransactionDateEnabled = isTransactionDateEnabled;
    }
}
