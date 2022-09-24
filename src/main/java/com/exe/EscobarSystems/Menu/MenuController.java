package com.exe.EscobarSystems.Menu;


import com.exe.EscobarSystems.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @PostMapping
    public Map<String, Object> getAllMenu(@RequestBody PaginationDto paginationDto){
        return menuService.getAllMenu(paginationDto);
    }

    @PostMapping("/active")
    public Map<String, Object> getAllActiveMenu(@RequestBody PaginationDto paginationDto){
        return menuService.getAllActiveMenu(paginationDto);
    }

    @PostMapping("/inactive")
    public Map<String, Object> getAllInactiveMenu(@RequestBody PaginationDto paginationDto){
        return menuService.getAllInactiveMenu(paginationDto);
    }

    @PostMapping("/activate")
    public void activateMenu(@RequestBody MenuListDto menuListDto){
        menuService.activateMenu(menuListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateMenu(@RequestBody MenuListDto menuListDto){
        menuService.inactivateMenu(menuListDto);
    }

    @PostMapping("/add")
    public void menuSupply(@RequestBody MenuDto menuDto){
        menuService.addMenu(menuDto);
    }

    @PutMapping("/update/{id}")
    public void updateMenu(@RequestBody MenuDto menuDto,
                           @PathVariable Long id){
        menuService.updateMenu(menuDto, id);
    }

    @GetMapping("/unavailable")
    public List<MenuDto> getAllUnavailableActiveMenu(){
        return menuService.getAllUnavailableActiveMenu();
    }
}
