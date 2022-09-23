package com.exe.EscobarSystems.Employee;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/active")
    public List<EmployeeDto> getAllActiveEmployees(){
        return employeeService.getAllActiveEmployees();
    }

    @GetMapping("/inactive")
    public List<EmployeeDto> getAllInactiveEmployees(){
        return employeeService.getAllInactiveEmployees();
    }

    @PostMapping("/activate")
    public void activateEmployees(@RequestBody EmployeeListDto employeeListDto){
        employeeService.activateEmployees(employeeListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateEmployees(@RequestBody EmployeeListDto employeeListDto){
        employeeService.inactivateEmployees(employeeListDto);
    }

    @PostMapping("/add")
    public void addEmployee(@RequestBody EmployeeDto employeeDto){
        employeeService.addEmployee(employeeDto);
    }

    @PutMapping("/update/{id}")
    public void updateEmployee(@RequestBody EmployeeDto employeeDto,
                               @PathVariable Long id){
        employeeService.updateEmployee(employeeDto, id);
    }


}
