package com.exe.EscobarSystems.Employee;

import com.exe.EscobarSystems.Employee.Exceptions.EmployeeIsExistingException;
import com.exe.EscobarSystems.Employee.Exceptions.EmployeeNotFoundException;
import com.exe.EscobarSystems.Employee.Exceptions.EmployeePositionIsNullException;
import com.exe.EscobarSystems.Employee.Exceptions.EmployeeTypeIsNullException;
import com.exe.EscobarSystems.EmployeePosition.EmployeePosition;
import com.exe.EscobarSystems.EmployeePosition.EmployeePositionDao;
import com.exe.EscobarSystems.EmployeePosition.Exceptions.EmployeePositionNameIsNullException;
import com.exe.EscobarSystems.EmployeePosition.Exceptions.EmployeePositionNotFoundException;
import com.exe.EscobarSystems.EmployeeType.EmployeeType;
import com.exe.EscobarSystems.EmployeeType.EmployeeTypeDao;
import com.exe.EscobarSystems.EmployeeType.Exceptions.EmployeeTypeNameIsNullException;
import com.exe.EscobarSystems.EmployeeType.Exceptions.EmployeeTypeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    @Qualifier("employee_mysql")
    EmployeeDao employeeRepository;

    @Autowired
    @Qualifier("employeePosition_mysql")
    EmployeePositionDao employeePositionRepository;

    @Autowired
    @Qualifier("employeeType_mysql")
    EmployeeTypeDao employeeTypeRepository;

    private EmployeeDto convertEntityToDto(Employee employee){
        return new EmployeeDto(
                employee.getEmployeeId(),
                employee.getEmployeeFirstName(),
                employee.getEmployeeLastName(),
                employee.getEmployeeAddress(),
                employee.getEmployeeContactNumber(),
                employee.getDateEmployed(),
                employee.getEmployeePosition().getEmployeePositionName(),
                employee.getEmployeeType().getEmployeeTypeName(),
                employee.getSuperiorEmployee() != null ? String.format("%s, %s",employee.getSuperiorEmployee().getEmployeeLastName(), employee.getSuperiorEmployee().getEmployeeFirstName()): null,
                employee.getIsActive()
        );
    }

    public List<EmployeeDto> getAllActiveEmployees(){
        List<EmployeeDto> employees = employeeRepository
                .getAllActiveEmployees()
                .stream()
                .map((employee) -> convertEntityToDto(employee))
                .collect(Collectors.toList());

        return employees;
    }

    public List<EmployeeDto> getAllInactiveEmployees(){
        List<EmployeeDto> employees = employeeRepository
                .getAllInactiveEmployees()
                .stream()
                .map((employee) -> convertEntityToDto(employee))
                .collect(Collectors.toList());

        return employees;
    }

     public void activateEmployees(EmployeeListDto employeeListDto){
        List<Long> employeeIds = employeeListDto
                .getEmployeeListDto()
                .stream()
                .map((employeeDto) -> employeeDto.getEmployeeId())
                .collect(Collectors.toList());
        employeeRepository.activateEmployees(employeeIds);
     }

    public void inactivateEmployees(EmployeeListDto employeeListDto){
        List<Long> employeeIds = employeeListDto
                .getEmployeeListDto()
                .stream()
                .map((employeeDto) -> employeeDto.getEmployeeId())
                .collect(Collectors.toList());
        employeeRepository.inactivateEmployees(employeeIds);
    }

    public void addEmployee(EmployeeDto employeeDto){
        String firstName = employeeDto.getEmployeeFirstName();
        String lastName = employeeDto.getEmployeeLastName();
        String address = employeeDto.getEmployeeAddress();
        String contactNumber = employeeDto.getEmployeeContactNumber();
        LocalDateTime dateEmployed = employeeDto.getDateEmployed();
        String employeePositionName = employeeDto.getEmployeePositionName();
        String employeeTypeName = employeeDto.getEmployeeTypeName();

        if (employeePositionName == null || employeePositionName.length() <= 0){
            throw new EmployeePositionIsNullException();
        }

        if (employeeTypeName == null || employeeTypeName.length() <= 0){
            throw new EmployeeTypeIsNullException();
        }

        EmployeePosition employeePosition = employeePositionRepository
                .getEmployeePositionByName(employeeDto.getEmployeePositionName())
                .orElseThrow(() -> new EmployeePositionNotFoundException(employeePositionName));

        EmployeeType employeeType = employeeTypeRepository.getEmployeeTypeByName(employeeDto.getEmployeeTypeName())
                        .orElseThrow(() -> new EmployeeTypeNotFoundException(employeeTypeName));

        String superiorName = employeeDto.getSuperiorEmployeeName();
        Employee superior = null;

        if (superiorName != null){
            String[] superiorSplit = superiorName.split(", ");
            String superiorLastName = superiorSplit[0];
            String superiorFirstName = superiorSplit[1];

            superior = employeeRepository.getEmployeeByFirstAndLastName(superiorFirstName, superiorLastName)
                    .orElseThrow(() -> new EmployeeNotFoundException(firstName, lastName));

        }

        Boolean isActive = employeeDto.getIsActive();

        Optional<Employee> employeeOptional = employeeRepository
                .getEmployeeByFirstAndLastName(firstName, lastName);

        if (employeeOptional.isPresent()){
            throw new EmployeeIsExistingException(firstName, lastName);
        }

        employeeRepository.insertEmployees(firstName,
                lastName,
                address,
                contactNumber,
                dateEmployed,
                employeePosition.getEmployeePositionId(),
                employeeType.getEmployeeTypeId(),
                superior != null ? superior.getEmployeeId(): null,
                isActive);
    }

    public void updateEmployee(EmployeeDto employeeDto, Long id){
        Employee employee = employeeRepository
                .getEmployeeById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        String firstName = employeeDto.getEmployeeFirstName();
        String lastName = employeeDto.getEmployeeLastName();
        String address = employeeDto.getEmployeeAddress();
        String contactNumber = employeeDto.getEmployeeContactNumber();
        LocalDateTime dateEmployed = employeeDto.getDateEmployed();
        String employeePositionName = employeeDto.getEmployeePositionName();
        String employeeTypeName = employeeDto.getEmployeeTypeName();


        if (employeePositionName == null || employeePositionName.length() <= 0){
            throw new EmployeePositionIsNullException();
        }

        if (employeeTypeName == null || employeeTypeName.length() <= 0){
            throw new EmployeeTypeIsNullException();
        }

        EmployeePosition employeePosition = employeePositionRepository
                .getEmployeePositionByName(employeeDto.getEmployeePositionName())
                .orElseThrow(() -> new EmployeePositionNotFoundException(employeePositionName));

        EmployeeType employeeType = employeeTypeRepository.getEmployeeTypeByName(employeeDto.getEmployeeTypeName())
                .orElseThrow(() -> new EmployeeTypeNotFoundException(employeeTypeName));

        String superiorName = employeeDto.getSuperiorEmployeeName();
        Employee superior = null;

        if (superiorName != null && superiorName.length() > 0){
            String[] superiorSplit = superiorName.split(", ");
            String superiorLastName = superiorSplit[0];
            String superiorFirstName = superiorSplit[1];

            superior = employeeRepository.getEmployeeByFirstAndLastName(superiorFirstName, superiorLastName)
                    .orElseThrow(() -> new EmployeeNotFoundException(firstName, lastName));
        }

        Boolean isActive = employeeDto.getIsActive();

        if (!Objects.equals(employee.getEmployeeFirstName(), firstName) ||
                !Objects.equals(employee.getEmployeeLastName(), lastName)){

            Optional<Employee> employeeOptional = employeeRepository
                    .getEmployeeByFirstAndLastName(firstName, lastName);

            if (employeeOptional.isPresent()){
                throw new EmployeeIsExistingException(firstName, lastName);
            }

            employee.setEmployeeFirstName(firstName);
            employee.setEmployeeLastName(lastName);
        }

        employee.setEmployeeAddress(address);
        employee.setEmployeeContactNumber(contactNumber);
        employee.setDateEmployed(dateEmployed);
        employee.setEmployeePosition(employeePosition);
        employee.setEmployeeType(employeeType);
        employee.setSuperiorEmployee(superior);
        employee.setIsActive(isActive);
    }
}
