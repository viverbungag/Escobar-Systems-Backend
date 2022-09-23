package com.exe.EscobarSystems.EmployeeType;

import com.exe.EscobarSystems.EmployeeType.Exceptions.EmployeeTypeNameIsExistingException;
import com.exe.EscobarSystems.EmployeeType.Exceptions.EmployeeTypeNameIsNullException;
import com.exe.EscobarSystems.EmployeeType.Exceptions.EmployeeTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeTypeService {

    @Autowired
    @Qualifier("employeeType_mysql")
    EmployeeTypeDao employeeTypeRepository;

    private EmployeeTypeDto convertEntityToDto(EmployeeType employeeType){
        return new EmployeeTypeDto(
                employeeType.getEmployeeTypeId(),
                employeeType.getEmployeeTypeName(),
                employeeType.getIsActive());
    }


    public List<String> getAllActiveEmployeeTypeNames(){
        return employeeTypeRepository
                .getAllActiveEmployeeTypesList()
                .stream()
                .map((EmployeeType employeeType)-> employeeType.getEmployeeTypeName())
                .collect(Collectors.toList());
    }

    public List<EmployeeTypeDto> getAllActiveEmployeeTypes(){
        List<EmployeeTypeDto> employeeTypePage = employeeTypeRepository
                .getAllActiveEmployeeTypes()
                .stream()
                .map((EmployeeType employeeType)-> convertEntityToDto(employeeType))
                .collect(Collectors.toList());

        return employeeTypePage;
    }

    public List<EmployeeTypeDto> getAllInactiveEmployeeTypes(){
        List<EmployeeTypeDto> employeeTypePage = employeeTypeRepository
                .getAllInactiveEmployeeTypes()
                .stream()
                .map((EmployeeType employeeType)-> convertEntityToDto(employeeType))
                .collect(Collectors.toList());

        return employeeTypePage;
    }

    public void addEmployeeType(EmployeeTypeDto employeeTypeDto) {
        String name = employeeTypeDto.getEmployeeTypeName();

        Optional<EmployeeType> employeeTypeOptional = employeeTypeRepository
                .getEmployeeTypeByName(name);

        if (name == null || name.length() <= 0){
            throw new EmployeeTypeNameIsNullException();
        }

        if (employeeTypeOptional.isPresent()){
            throw new EmployeeTypeNameIsExistingException(name);
        }

        employeeTypeRepository.insertEmployeeType(
                employeeTypeDto.getEmployeeTypeName(),
                employeeTypeDto.getIsActive());
    }

    public void inactivateEmployeeType(EmployeeTypeListDto employeeTypeListDto){
        List<String> employeeTypeNames = employeeTypeListDto
                .getEmployeeTypeListDto()
                .stream()
                .map((employeeTypeDto) -> employeeTypeDto.getEmployeeTypeName())
                .collect(Collectors.toList());

        employeeTypeRepository.inactivateEmployeeType(employeeTypeNames);
    }

    public void activateEmployeeType(EmployeeTypeListDto employeeTypeListDto){
        List<String> employeeTypeNames = employeeTypeListDto
                .getEmployeeTypeListDto()
                .stream()
                .map((employeeTypeDto) -> employeeTypeDto.getEmployeeTypeName())
                .collect(Collectors.toList());

        employeeTypeRepository.activateEmployeeType(employeeTypeNames);
    }

    public void updateEmployeeType(EmployeeTypeDto employeeTypeDto, Long id) {

        EmployeeType employeeType = employeeTypeRepository.getEmployeeTypeById(id)
                .orElseThrow(() -> new EmployeeTypeNotFoundException(id));

        String name = employeeTypeDto.getEmployeeTypeName();
        Boolean active = employeeTypeDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new EmployeeTypeNameIsNullException();
        }

        if (!Objects.equals(employeeType.getEmployeeTypeName(), name)) {

            Optional<EmployeeType> employeeTypeOptional = employeeTypeRepository
                    .getEmployeeTypeByName(name);

            if (employeeTypeOptional.isPresent()){
                throw new EmployeeTypeNameIsExistingException(name);
            }

            employeeType.setEmployeeTypeName(name);
        }

        employeeType.setIsActive(active);
    }
}
