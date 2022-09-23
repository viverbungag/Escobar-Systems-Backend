package com.exe.EscobarSystems.Supply;

import com.exe.EscobarSystems.Pagination.PaginationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("api/v1/supply")
public class SupplyController {

    @Autowired
    SupplyService supplyService;

    @GetMapping
    public List<SupplyDtoV2> getAllSupplies(){
        return supplyService.getAllActiveSuppliesWithoutPagination();
    }

    @GetMapping("/names")
    public List<String> getAllSupplyNames(){
        return supplyService.getAllSupplyNames();
    }

    @GetMapping("/with-suppliers")
    public List<SupplyDtoV3> getAllSuppliesWithSuppliers(){
        return supplyService.getAllActiveSuppliesWithSuppliersWithoutPagination();
    }

    @PostMapping
    public Map<String, Object> getAllSupplies(@RequestBody PaginationDto paginationDto){
        return supplyService.getAllSupplies(paginationDto);
    }

    @PostMapping("/active")
    public Map<String, Object> getAllActiveSupplies(@RequestBody PaginationDto paginationDto){
        return supplyService.getAllActiveSupplies(paginationDto);
    }

    @PostMapping("/active/in-minimum")
    public Map<String, Object> getAllActiveInMinimumSupplies(@RequestBody PaginationDto paginationDto){
        return supplyService.getAllActiveInMinimumSupplies(paginationDto);
    }

    @PostMapping("/inactive")
    public Map<String, Object> getAllInactiveSupplies(@RequestBody PaginationDto paginationDto){
        return supplyService.getAllInactiveSupplies(paginationDto);
    }

    @PostMapping("/activate")
    public void activateMenuCategory(@RequestBody SupplyListDto supplyListDto){
        supplyService.activateSupply(supplyListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateMenuCategory(@RequestBody SupplyListDto supplyListDto){
        supplyService.inactivateSupply(supplyListDto);
    }

    @PostMapping("/add")
    public void addSupply(@RequestBody SupplyDto supplyDto){
        supplyService.addSupply(supplyDto);
    }

    @PutMapping("/update/{id}")
    public void updateSupply(@RequestBody SupplyDto supplyDto,
                             @PathVariable Long id){
        supplyService.updateSupply(supplyDto, id);
    }
}
