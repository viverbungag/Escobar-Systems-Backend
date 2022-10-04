package com.exe.EscobarSystems.Order;


import com.exe.EscobarSystems.Menu.MenuDto;
import com.exe.EscobarSystems.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/paged")
    public Map<String, Object> getAllPagedOrders(@RequestBody PaginationDto paginationDto){
        return orderService.getAllPagedOrders(paginationDto);
    }

    @PostMapping("/paged/paid")
    public Map<String, Object> getAllPagedPaidOrders(@RequestBody PaginationDto paginationDto){
        return orderService.getAllPagedPaidOrders(paginationDto);
    }

    @PostMapping("/paged/unpaid")
    public Map<String, Object> getAllPagedUnpaidOrders(@RequestBody PaginationDto paginationDto){
        return orderService.getAllPagedUnpaidOrders(paginationDto);
    }

    @GetMapping("/today")
    public List<OrderDto> getAllOrdersToday(){
        return orderService.getAllOrdersToday();
    }

    @PostMapping("/menu-on-category")
    public List<MenuDto> getMenuBasedOnCategory(@RequestBody MenuOnCategoryDto menuOnCategoryDto){
        System.out.println(menuOnCategoryDto);
        return orderService.getMenuBasedOnCategory(menuOnCategoryDto);
    }

    @PostMapping("/menu")
    public List<MenuDto> getAllMenu(@RequestBody MenuOnCategoryDto menuOnCategoryDto){
        return orderService.getAllMenus(menuOnCategoryDto);
    }

    @PostMapping("/add")
    public void addOrder (@RequestBody OrderDto orderDto){
       orderService.addOrder(orderDto);
    }

    @PostMapping("/pay/{orderId}")
    public void payOrder (@RequestBody OrderDto orderDto, @PathVariable Long orderId){
        orderService.payOrder(orderDto, orderId);
    }

    @GetMapping("/unavailable-table-numbers")
    public List<Integer> getUnavailableTableNumbers(){
        return orderService.getUnavailableTableNumbers();
    };

    @PostMapping("/add/existing/{orderId}")
    public void addToExistingOrder (@RequestBody OrderDto orderDto, @PathVariable Long orderId){
        orderService.addToExistingOrder(orderDto, orderId);
    }

    @DeleteMapping("/void/{orderId}")
    public void voidOrder (@PathVariable Long orderId){
        orderService.voidOrder(orderId);
    }

}
