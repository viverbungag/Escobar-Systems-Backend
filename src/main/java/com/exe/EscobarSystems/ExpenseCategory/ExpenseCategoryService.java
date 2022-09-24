package com.exe.EscobarSystems.ExpenseCategory;

import com.exe.EscobarSystems.ExpenseCategory.Exceptions.ExpenseCategoryNameIsExistingException;
import com.exe.EscobarSystems.ExpenseCategory.Exceptions.ExpenseCategoryNameIsNullException;
import com.exe.EscobarSystems.ExpenseCategory.Exceptions.ExpenseCategoryNotFoundException;
import com.exe.EscobarSystems.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpenseCategoryService {

    @Autowired
    @Qualifier("expenseCategory_mysql")
    ExpenseCategoryDao expenseCategoryRepository;

    private ExpenseCategoryDto convertEntityToDto(ExpenseCategory expenseCategory){
        return new ExpenseCategoryDto(
                expenseCategory.getExpenseCategoryId(),
                expenseCategory.getExpenseCategoryName(),
                expenseCategory.getIsActive());
    }

    private Sort getSortingMethod(Boolean isAscending, Sort sort){
        if (isAscending){
            return sort.ascending();
        }
        return sort.descending();
    }

    private Sort getSortingValue(String sortedBy){

        switch(sortedBy){
            case "Name":
                return Sort.by("expense_category_name");
            case "None":
                return Sort.by("expense_category_id");
            default:
                return Sort.unsorted();
        }
    }

    private Pageable initializePageable(PaginationDto paginationDto){
        int pageNo = paginationDto.getPageNo();
        int pageSize = paginationDto.getPageSize();
        Boolean isAscending = paginationDto.getIsAscending();
        String sortedBy = paginationDto.getSortedBy();

        Sort sort = getSortingValue(sortedBy);
        Sort finalSort = getSortingMethod(isAscending, sort);

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, finalSort);

        return pageable;
    }

    private Map<String, Object> initializeExpenseCategoryWithPageDetails(Page<ExpenseCategory> expenseCategoryPage, PaginationDto paginationDto){
        Integer pageNo = paginationDto.getPageNo();
        Integer totalPages = expenseCategoryPage.getTotalPages();
        Long totalCount = expenseCategoryPage.getTotalElements();

        Map<String, Object> expenseCategoryWithPageDetails = new HashMap<>();

        if (pageNo < 1 || pageNo > totalPages){
            expenseCategoryWithPageDetails.put("contents", new ArrayList<>());
            expenseCategoryWithPageDetails.put("totalPages", 0);
            expenseCategoryWithPageDetails.put("totalCount", 0);
            return expenseCategoryWithPageDetails;
        }

        expenseCategoryWithPageDetails.put("contents",
                expenseCategoryPage
                        .getContent()
                        .stream()
                        .map((ExpenseCategory expenseCategory)-> convertEntityToDto(expenseCategory))
                        .collect(Collectors.toList()));

        expenseCategoryWithPageDetails.put("totalPages", totalPages);
        expenseCategoryWithPageDetails.put("totalCount", totalCount);

        return expenseCategoryWithPageDetails;
    }



    public Map<String, Object> getAllExpenseCategories(PaginationDto paginationDto){
        Pageable pageable = initializePageable(paginationDto);
        Page<ExpenseCategory> expenseCategoryPage = expenseCategoryRepository
                .getAllExpenseCategories(pageable);

        return initializeExpenseCategoryWithPageDetails(expenseCategoryPage, paginationDto);
    }

    public List<String> getAllActiveExpenseCategoryNames(){
        return expenseCategoryRepository
                .getAllActiveExpenseCategoriesList()
                .stream()
                .map((ExpenseCategory supplyCategory)-> supplyCategory.getExpenseCategoryName())
                .collect(Collectors.toList());
    }

    public List<ExpenseCategoryDto> getAllActiveExpenseCategories(){
        List<ExpenseCategoryDto> supplyCategoryPage = expenseCategoryRepository
                .getAllActiveExpenseCategories()
                .stream()
                .map((expenseCategory) -> convertEntityToDto(expenseCategory))
                .collect(Collectors.toList());

        return supplyCategoryPage;
    }

    public List<ExpenseCategoryDto> getAllInactiveExpenseCategories(){
        List<ExpenseCategoryDto> supplyCategoryPage = expenseCategoryRepository
                .getAllInactiveExpenseCategories()
                .stream()
                .map((expenseCategory) -> convertEntityToDto(expenseCategory))
                .collect(Collectors.toList());;

        return supplyCategoryPage;
    }

    public void addExpenseCategory(ExpenseCategoryDto expenseCategoryDto) {
        String name = expenseCategoryDto.getExpenseCategoryName();

        Optional<ExpenseCategory> expenseCategoryOptional = expenseCategoryRepository
                .getExpenseCategoryByName(name);

        if (name == null || name.length() <= 0){
            throw new ExpenseCategoryNameIsNullException();
        }

        if (expenseCategoryOptional.isPresent()){
            throw new ExpenseCategoryNameIsExistingException(name);
        }

        expenseCategoryRepository.insertExpenseCategory(
                expenseCategoryDto.getExpenseCategoryName(),
                expenseCategoryDto.getIsActive());
    }

    public void inactivateExpenseCategory(ExpenseCategoryListDto expenseCategoryListDto){
        List<String> expenseCategoryNames = expenseCategoryListDto
                .getExpenseCategoryListDto()
                .stream()
                .map((expenseCategoryDto) -> expenseCategoryDto.getExpenseCategoryName())
                .collect(Collectors.toList());

        expenseCategoryRepository.inactivateExpenseCategory(expenseCategoryNames);
    }

    public void activateExpenseCategory(ExpenseCategoryListDto expenseCategoryListDto){
        List<String> expenseCategoryNames = expenseCategoryListDto
                .getExpenseCategoryListDto()
                .stream()
                .map((menuCategoryDto) -> menuCategoryDto.getExpenseCategoryName())
                .collect(Collectors.toList());

        expenseCategoryRepository.activateExpenseCategory(expenseCategoryNames);
    }

    public void updateExpenseCategory(ExpenseCategoryDto expenseCategoryDto, Long id) {

        ExpenseCategory expenseCategory = expenseCategoryRepository.getExpenseCategoryById(id)
                .orElseThrow(() -> new ExpenseCategoryNotFoundException(id));

        String name = expenseCategoryDto.getExpenseCategoryName();
        Boolean active = expenseCategoryDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new ExpenseCategoryNameIsNullException();
        }

        if (!Objects.equals(expenseCategory.getExpenseCategoryName(), name)) {

            Optional<ExpenseCategory> supplyCategoryOptional = expenseCategoryRepository
                    .getExpenseCategoryByName(name);

            if (supplyCategoryOptional.isPresent()){
                throw new ExpenseCategoryNameIsExistingException(name);
            }

            expenseCategory.setExpenseCategoryName(name);
        }

        expenseCategory.setIsActive(active);
    }
}
