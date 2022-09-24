package com.exe.EscobarSystems.ExpenseCategory;

import com.exe.EscobarSystems.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/expense-category")
public class ExpenseCategoryController {

    @Autowired
    ExpenseCategoryService expenseCategoryService;

    @GetMapping
    public List<String> getAllActiveExpenseCategoryNames(){
        return expenseCategoryService.getAllActiveExpenseCategoryNames();
    }

    @PostMapping("/paged")
    public Map<String, Object> getAllExpenseCategories(@RequestBody PaginationDto paginationDto){
        return expenseCategoryService.getAllExpenseCategories(paginationDto);
    }

    @GetMapping("/active")
    public List<ExpenseCategoryDto> getAllActiveExpenseCategories(){
        return expenseCategoryService.getAllActiveExpenseCategories();
    }

    @GetMapping("/inactive")
    public List<ExpenseCategoryDto> getAllInactiveExpenseCategories(){
        return expenseCategoryService.getAllInactiveExpenseCategories();
    }

    @PostMapping("/activate")
    public void activateExpenseCategory(@RequestBody ExpenseCategoryListDto expenseCategoryListDto){
        expenseCategoryService.activateExpenseCategory(expenseCategoryListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateExpenseCategory(@RequestBody ExpenseCategoryListDto expenseCategoryListDto){
        expenseCategoryService.inactivateExpenseCategory(expenseCategoryListDto);
    }

    @PostMapping("/add")
    public void addExpenseCategory(@RequestBody ExpenseCategoryDto expenseCategoryDto){
        expenseCategoryService.addExpenseCategory(expenseCategoryDto);
    }

    @PutMapping("/update/{id}")
    public void updateExpenseCategory(@RequestBody ExpenseCategoryDto expenseCategoryDto, @PathVariable Long id){
        expenseCategoryService.updateExpenseCategory(expenseCategoryDto, id);
    }



}
