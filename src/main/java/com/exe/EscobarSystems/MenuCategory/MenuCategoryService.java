package com.exe.EscobarSystems.MenuCategory;


import com.exe.EscobarSystems.MenuCategory.Exceptions.MenuCategoryNameIsExistingException;
import com.exe.EscobarSystems.MenuCategory.Exceptions.MenuCategoryNameIsNullException;
import com.exe.EscobarSystems.MenuCategory.Exceptions.MenuCategoryNotFoundException;
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
public class MenuCategoryService {

    @Autowired
    @Qualifier("menuCategory_mysql")
    MenuCategoryDao menuCategoryRepository;

    private MenuCategoryDto convertEntityToDto(MenuCategory menuCategory){
        return new MenuCategoryDto(
                menuCategory.getMenuCategoryId(),
                menuCategory.getMenuCategoryName(),
                menuCategory.getIsActive());
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
                return Sort.by("menu_category_name");
            case "None":
                return Sort.by("menu_category_id");
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

    private Map<String, Object> initializeMenuCategoryWithPageDetails(Page<MenuCategory> menuCategoryPage, PaginationDto paginationDto){
        Integer pageNo = paginationDto.getPageNo();
        Integer totalPages = menuCategoryPage.getTotalPages();
        Long totalCount = menuCategoryPage.getTotalElements();

        Map<String, Object> menuCategoryWithPageDetails = new HashMap<>();

        if (pageNo < 1 || pageNo > totalPages){
            menuCategoryWithPageDetails.put("contents", new ArrayList<>());
            menuCategoryWithPageDetails.put("totalPages", 0);
            menuCategoryWithPageDetails.put("totalCount", 0);
            return menuCategoryWithPageDetails;
        }

        menuCategoryWithPageDetails.put("contents",
                menuCategoryPage
                        .getContent()
                        .stream()
                        .map((MenuCategory menuCategory)-> convertEntityToDto(menuCategory))
                        .collect(Collectors.toList()));

        menuCategoryWithPageDetails.put("totalPages", totalPages);
        menuCategoryWithPageDetails.put("totalCount", totalCount);



        return menuCategoryWithPageDetails;
    }

    public Map<String, Object> getAllMenuCategories(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<MenuCategory> menuCategoryPage = menuCategoryRepository
                .getAllMenuCategories(pageable);

        return initializeMenuCategoryWithPageDetails(menuCategoryPage, paginationDto);
    }

    public List<String> getAllActiveMenuCategoryNames(){
        return menuCategoryRepository
                .getAllActiveMenuCategoriesList()
                .stream()
                .map((MenuCategory menuCategory)-> menuCategory.getMenuCategoryName())
                .collect(Collectors.toList());
    }

    public Map<String, Object> getAllActiveMenuCategories(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<MenuCategory> menuCategoryPage = menuCategoryRepository
                .getAllActiveMenuCategories(pageable);

        return initializeMenuCategoryWithPageDetails(menuCategoryPage, paginationDto);
    }

    public Map<String, Object> getAllInactiveMenuCategories(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<MenuCategory> menuCategoryPage = menuCategoryRepository
                .getAllInactiveMenuCategories(pageable);

        return initializeMenuCategoryWithPageDetails(menuCategoryPage, paginationDto);
    }

    public void addMenuCategory(MenuCategoryDto menuCategoryDto) {
        String name = menuCategoryDto.getMenuCategoryName();

        Optional<MenuCategory> menuCategoryOptional = menuCategoryRepository
                .getMenuCategoryByName(name);

        if (name == null || name.length() <= 0){
            throw new MenuCategoryNameIsNullException();
        }

        if (menuCategoryOptional.isPresent()){
            throw new MenuCategoryNameIsExistingException(name);
        }

        menuCategoryRepository.insertMenuCategory(
                menuCategoryDto.getMenuCategoryName(),
                menuCategoryDto.getIsActive()
        );
    }

    public void inactivateMenuCategory(MenuCategoryListDto menuCategoryListDto){
        List<String> menuCategoryNames = menuCategoryListDto
                .getMenuCategoryListDto()
                .stream()
                .map((menuCategoryDto) -> menuCategoryDto.getMenuCategoryName())
                .collect(Collectors.toList());

        menuCategoryRepository.inactivateMenuCategory(menuCategoryNames);
    }

    public void activateMenuCategory(MenuCategoryListDto menuCategoryListDto) {
        List<String> menuCategoryNames = menuCategoryListDto
                .getMenuCategoryListDto()
                .stream()
                .map((menuCategoryDto) -> menuCategoryDto.getMenuCategoryName())
                .collect(Collectors.toList());

        menuCategoryRepository.activateMenuCategory(menuCategoryNames);
    }

    public void updateMenuCategory(MenuCategoryDto menuCategoryDto, Long id) {

        MenuCategory menuCategory = menuCategoryRepository.getMenuCategoryById(id)
                .orElseThrow(() -> new MenuCategoryNotFoundException(id));

        String name = menuCategoryDto.getMenuCategoryName();
        Boolean active = menuCategoryDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new MenuCategoryNameIsNullException();
        }

        if (!Objects.equals(menuCategory.getMenuCategoryName(), name)){

            Optional<MenuCategory> menuCategoryOptional = menuCategoryRepository
                    .getMenuCategoryByName(name);

            if (menuCategoryOptional.isPresent()){
                throw new MenuCategoryNameIsExistingException(name);
            }

            menuCategory.setMenuCategoryName(name);
        }

        menuCategory.setIsActive(active);
    }
}
