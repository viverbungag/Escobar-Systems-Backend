package com.exe.EscobarSystems.MenuCategory;


import com.exe.EscobarSystems.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/menu-category")
public class MenuCategoryController {

    @Autowired
    MenuCategoryService menuCategoryService;

    @GetMapping
    public List<String> getAllActiveMenuCategoryNames(){
        return menuCategoryService.getAllActiveMenuCategoryNames();
    }

    @PostMapping
    public Map<String, Object> getAllMenuCategories(@RequestBody PaginationDto paginationDto){
        return menuCategoryService.getAllMenuCategories(paginationDto);
    }

    @PostMapping("/active")
    public Map<String, Object> getAllActiveMenuCategories(@RequestBody PaginationDto paginationDto){
        return menuCategoryService.getAllActiveMenuCategories(paginationDto);
    }

    @PostMapping("/inactive")
    public Map<String, Object> getAllInactiveMenuCategories(@RequestBody PaginationDto paginationDto){
        return menuCategoryService.getAllInactiveMenuCategories(paginationDto);
    }

    @PostMapping("/activate")
    public void activateMenuCategory(@RequestBody MenuCategoryListDto menuCategoryListDto){
        menuCategoryService.activateMenuCategory(menuCategoryListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateMenuCategory(@RequestBody MenuCategoryListDto menuCategoryListDto){
        menuCategoryService.inactivateMenuCategory(menuCategoryListDto);
    }

    @PostMapping("/add")
    public void addMenuCategory(@RequestBody MenuCategoryDto menuCategoryDto){
        menuCategoryService.addMenuCategory(menuCategoryDto);
    }

    @PutMapping("/update/{id}")
    public void updateMenuCategory(@RequestBody MenuCategoryDto menuCategoryDto, @PathVariable Long id){
        menuCategoryService.updateMenuCategory(menuCategoryDto, id);
    }

}
