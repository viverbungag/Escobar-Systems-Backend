package com.exe.EscobarSystems.SupplyCategory;


import com.exe.EscobarSystems.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/supply-category")
public class SupplyCategoryController {

    @Autowired
    SupplyCategoryService supplyCategoryService;

    @GetMapping
    public List<String> getAllActiveSupplyCategoryNames(){
        return supplyCategoryService.getAllActiveSupplyCategoryNames();
    }

    @PostMapping
    public Map<String, Object> getAllSupplyCategories(@RequestBody PaginationDto paginationDto){
        return supplyCategoryService.getAllSupplyCategories(paginationDto);
    }

    @PostMapping("/active")
    public Map<String, Object> getAllActiveSupplyCategories(@RequestBody PaginationDto paginationDto){
        return supplyCategoryService.getAllActiveSupplyCategories(paginationDto);
    }

    @PostMapping("/inactive")
    public Map<String, Object> getAllInactiveSupplyCategories(@RequestBody PaginationDto paginationDto){
        return supplyCategoryService.getAllInactiveSupplyCategories(paginationDto);
    }

    @PostMapping("/activate")
    public void activateSupplyCategory(@RequestBody SupplyCategoryListDto supplyCategoryListDto){
        supplyCategoryService.activateSupplyCategory(supplyCategoryListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateSupplyCategory(@RequestBody SupplyCategoryListDto supplyCategoryListDto){
        supplyCategoryService.inactivateSupplyCategory(supplyCategoryListDto);
    }

    @PostMapping("/add")
    public void addSupplyCategory(@RequestBody SupplyCategoryDto supplyCategoryDto){
        supplyCategoryService.addSupplyCategory(supplyCategoryDto);
    }

    @PutMapping("/update/{id}")
    public void updateSupplyCategory(@RequestBody SupplyCategoryDto supplyCategoryDto, @PathVariable Long id){
        supplyCategoryService.updateSupplyCategory(supplyCategoryDto, id);
    }


}
