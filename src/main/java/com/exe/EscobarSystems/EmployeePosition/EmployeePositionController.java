package com.exe.EscobarSystems.EmployeePosition;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/employee-position")
public class EmployeePositionController {

    @Autowired
    EmployeePositionService employeePositionService;

    @GetMapping
    public List<String> getAllActiveEmployeePositionNames(){
        return employeePositionService.getAllActiveEmployeePositionNames();
    }

    @GetMapping("/active")
    public List<EmployeePositionDto> getAllActiveEmployeePositions(){
        return employeePositionService.getAllActiveEmployeePositions();
    }

    @GetMapping("/inactive")
    public List<EmployeePositionDto> getAllInactiveEmployeePositions(){
        return employeePositionService.getAllInactiveEmployeePositions();
    }

    @PostMapping("/activate")
    public void activateEmployeePosition(@RequestBody EmployeePositionListDto employeePositionListDto){
        employeePositionService.activateEmployeePosition(employeePositionListDto);
    }

    @PostMapping("/inactivate")
    public void inactivateEmployeePosition(@RequestBody EmployeePositionListDto employeePositionListDto){
        System.out.println(employeePositionListDto);
        employeePositionService.inactivateEmployeePosition(employeePositionListDto);
    }

    @PostMapping("/add")
    public void addEmployeePosition(@RequestBody EmployeePositionDto employeePositionDto){
        employeePositionService.addEmployeePosition(employeePositionDto);
    }

    @PutMapping("/update/{id}")
    public void updateEmployeePosition(@RequestBody EmployeePositionDto employeePositionDto, @PathVariable Long id){
        employeePositionService.updateEmployeePosition(employeePositionDto, id);
    }
}
