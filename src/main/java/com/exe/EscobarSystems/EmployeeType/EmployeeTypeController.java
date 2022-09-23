package com.exe.EscobarSystems.EmployeeType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/employee-type")
public class EmployeeTypeController {

    @Autowired
    EmployeeTypeService employeeTypeService;

    @GetMapping
    public List<String> getAllActiveEmployeeTypeNames(){
        return employeeTypeService.getAllActiveEmployeeTypeNames();
    }

    @GetMapping("/active")
    public List<EmployeeTypeDto> getAllActiveEmployeeTypes(){
        return employeeTypeService.getAllActiveEmployeeTypes();
    }

    @GetMapping("/inactive")
    public List<EmployeeTypeDto> getAllInactiveEmployeeTypes(){
        return employeeTypeService.getAllInactiveEmployeeTypes();
    }

    @PostMapping("/activate")
    public void activateEmployeeType(@RequestBody EmployeeTypeListDto employeeTypeListDto){
        employeeTypeService.activateEmployeeType(employeeTypeListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateEmployeeType(@RequestBody EmployeeTypeListDto employeeTypeListDto){
        employeeTypeService.inactivateEmployeeType(employeeTypeListDto);
    }

    @PostMapping("/add")
    public void addEmployeeType(@RequestBody EmployeeTypeDto employeeTypeDto){
        employeeTypeService.addEmployeeType(employeeTypeDto);
    }

    @PutMapping("/update/{id}")
    public void updateEmployeeType(@RequestBody EmployeeTypeDto employeeTypeDto, @PathVariable Long id){
        employeeTypeService.updateEmployeeType(employeeTypeDto, id);
    }
}
