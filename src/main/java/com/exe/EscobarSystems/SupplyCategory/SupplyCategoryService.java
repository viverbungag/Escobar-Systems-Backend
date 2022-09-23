package com.exe.EscobarSystems.SupplyCategory;

import com.exe.EscobarSystems.Pagination.PaginationDto;
import com.exe.EscobarSystems.SupplyCategory.Exceptions.SupplyCategoryNameIsExistingException;
import com.exe.EscobarSystems.SupplyCategory.Exceptions.SupplyCategoryNameIsNullException;
import com.exe.EscobarSystems.SupplyCategory.Exceptions.SupplyCategoryNotFoundException;
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
public class SupplyCategoryService {

    @Autowired
    @Qualifier("supplyCategory_mysql")
    SupplyCategoryDao supplyCategoryRepository;

    private SupplyCategoryDto convertEntityToDto(SupplyCategory supplyCategory){
        return new SupplyCategoryDto(
                supplyCategory.getSupplyCategoryId(),
                supplyCategory.getSupplyCategoryName(),
                supplyCategory.getIsActive());
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
                return Sort.by("supply_category_name");
            case "None":
                return Sort.by("supply_category_id");
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

    private Map<String, Object> initializeSupplyCategoryWithPageDetails(Page<SupplyCategory> supplyCategoryPage, PaginationDto paginationDto){
        Integer pageNo = paginationDto.getPageNo();
        Integer totalPages = supplyCategoryPage.getTotalPages();
        Long totalCount = supplyCategoryPage.getTotalElements();

        Map<String, Object> supplyCategoryWithPageDetails = new HashMap<>();

        if (pageNo < 1 || pageNo > totalPages){
            supplyCategoryWithPageDetails.put("contents", new ArrayList<>());
            supplyCategoryWithPageDetails.put("totalPages", 0);
            supplyCategoryWithPageDetails.put("totalCount", 0);
            return supplyCategoryWithPageDetails;
        }

        supplyCategoryWithPageDetails.put("contents",
                supplyCategoryPage
                        .getContent()
                        .stream()
                        .map((SupplyCategory supplyCategory)-> convertEntityToDto(supplyCategory))
                        .collect(Collectors.toList()));

        supplyCategoryWithPageDetails.put("totalPages", totalPages);
        supplyCategoryWithPageDetails.put("totalCount", totalCount);

        return supplyCategoryWithPageDetails;
    }



    public Map<String, Object> getAllSupplyCategories(PaginationDto paginationDto){
        Pageable pageable = initializePageable(paginationDto);
        Page<SupplyCategory> supplyCategoryPage = supplyCategoryRepository
                .getAllSupplyCategories(pageable);

        return initializeSupplyCategoryWithPageDetails(supplyCategoryPage, paginationDto);
    }

    public List<String> getAllActiveSupplyCategoryNames(){
        return supplyCategoryRepository
                .getAllActiveSupplyCategoriesList()
                .stream()
                .map((SupplyCategory supplyCategory)-> supplyCategory.getSupplyCategoryName())
                .collect(Collectors.toList());
    }

    public Map<String, Object> getAllActiveSupplyCategories(PaginationDto paginationDto){
        Pageable pageable = initializePageable(paginationDto);
        Page<SupplyCategory> supplyCategoryPage = supplyCategoryRepository
                .getAllActiveSupplyCategories(pageable);

        return initializeSupplyCategoryWithPageDetails(supplyCategoryPage, paginationDto);
    }

    public Map<String, Object> getAllInactiveSupplyCategories(PaginationDto paginationDto){
        Pageable pageable = initializePageable(paginationDto);
        Page<SupplyCategory> supplyCategoryPage = supplyCategoryRepository
                .getAllInactiveSupplyCategories(pageable);

        return initializeSupplyCategoryWithPageDetails(supplyCategoryPage, paginationDto);
    }

    public void addSupplyCategory(SupplyCategoryDto supplyCategoryDto) {
        String name = supplyCategoryDto.getSupplyCategoryName();

        Optional<SupplyCategory> supplyCategoryOptional = supplyCategoryRepository
                .getSupplyCategoryByName(name);

        if (name == null || name.length() <= 0){
            throw new SupplyCategoryNameIsNullException();
        }

        if (supplyCategoryOptional.isPresent()){
            throw new SupplyCategoryNameIsExistingException(name);
        }

        supplyCategoryRepository.insertSupplyCategory(
                supplyCategoryDto.getSupplyCategoryName(),
                supplyCategoryDto.getIsActive());
    }

    public void inactivateSupplyCategory(SupplyCategoryListDto supplyCategoryListDto){
        List<String> supplyCategoryNames = supplyCategoryListDto
                .getSupplyCategoryListDto()
                .stream()
                .map((menuCategoryDto) -> menuCategoryDto.getSupplyCategoryName())
                .collect(Collectors.toList());

        supplyCategoryRepository.inactivateSupplyCategory(supplyCategoryNames);
    }

    public void activateSupplyCategory(SupplyCategoryListDto supplyCategoryListDto){
        List<String> supplyCategoryNames = supplyCategoryListDto
                .getSupplyCategoryListDto()
                .stream()
                .map((menuCategoryDto) -> menuCategoryDto.getSupplyCategoryName())
                .collect(Collectors.toList());

        supplyCategoryRepository.activateSupplyCategory(supplyCategoryNames);
    }

    public void updateSupplyCategory(SupplyCategoryDto supplyCategoryDto, Long id) {

        SupplyCategory supplyCategory = supplyCategoryRepository.getSupplyCategoryById(id)
                .orElseThrow(() -> new SupplyCategoryNotFoundException(id));

        String name = supplyCategoryDto.getSupplyCategoryName();
        Boolean active = supplyCategoryDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new SupplyCategoryNameIsNullException();
        }

        if (!Objects.equals(supplyCategory.getSupplyCategoryName(), name)) {

            Optional<SupplyCategory> supplyCategoryOptional = supplyCategoryRepository
                    .getSupplyCategoryByName(name);

            if (supplyCategoryOptional.isPresent()){
                throw new SupplyCategoryNameIsExistingException(name);
            }

            supplyCategory.setSupplyCategoryName(name);
        }

        supplyCategory.setIsActive(active);
    }
}
