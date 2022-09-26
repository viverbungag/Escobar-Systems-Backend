package com.exe.EscobarSystems.Transaction;

import com.exe.EscobarSystems.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;


    @PostMapping
    public Map<String, Object> getAllTransactions(@RequestBody TransactionFiltersPaginationDto transactionFiltersPaginationDto){
        return transactionService.getAllPagedTransactions(transactionFiltersPaginationDto);
    }

    @PostMapping("/stock-in")
    public void stockInTransaction(@RequestBody TransactionDto transactionDto){
        transactionService.stockInTransaction(transactionDto);
    }

    @PostMapping("/stock-out")
    public void stockOutTransaction(@RequestBody TransactionDto transactionDto){
        transactionService.stockOutTransaction(transactionDto);
    }

    @PostMapping("/expired/stock-out")
    public void stockOutExpiredTransaction(@RequestBody TransactionDto transactionDto){
        transactionService.stockOutExpiredTransaction(transactionDto);
    }

    @PostMapping("/expired")
    public Map<String, Object> getExpiredTransaction(@RequestBody PaginationDto paginationDto){
        return transactionService.getExpiredTransaction(paginationDto);
    }

    @PostMapping("/print")
    public void printTransaction(@RequestBody TransactionPrintDetailsDto transactionPrintDetailsDto){
        transactionService.printTransaction(transactionPrintDetailsDto);
    }
}
