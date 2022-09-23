package com.exe.EscobarSystems.EmployeePosition;

import com.exe.EscobarSystems.EmployeePosition.Exceptions.EmployeePositionNameIsExistingException;
import com.exe.EscobarSystems.EmployeePosition.Exceptions.EmployeePositionNameIsNullException;
import com.exe.EscobarSystems.EmployeePosition.Exceptions.EmployeePositionNotFoundException;
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
public class EmployeePositionService {

    @Autowired
    @Qualifier("employeePosition_mysql")
    EmployeePositionDao employeePositionRepository;

    private EmployeePositionDto convertEntityToDto(EmployeePosition employeePosition){
        return new EmployeePositionDto(
                employeePosition.getEmployeePositionId(),
                employeePosition.getEmployeePositionName(),
                employeePosition.getIsActive());
    }


    public List<String> getAllActiveEmployeePositionNames(){
        return employeePositionRepository
                .getAllActiveEmployeePositionsList()
                .stream()
                .map((EmployeePosition employeePosition)-> employeePosition.getEmployeePositionName())
                .collect(Collectors.toList());
    }

    public List<EmployeePositionDto> getAllActiveEmployeePositions(){
        List<EmployeePositionDto> employeePositionPage = employeePositionRepository
                .getAllActiveEmployeePositions()
                .stream()
                .map((EmployeePosition employeePosition)-> convertEntityToDto(employeePosition))
                .collect(Collectors.toList());

        return employeePositionPage;
    }

    public List<EmployeePositionDto> getAllInactiveEmployeePositions(){
        List<EmployeePositionDto> employeePositionPage = employeePositionRepository
                .getAllInactiveEmployeePositions()
                .stream()
                .map((EmployeePosition employeePosition)-> convertEntityToDto(employeePosition))
                .collect(Collectors.toList());

        return employeePositionPage;
    }

    public void addEmployeePosition(EmployeePositionDto employeePositionDto) {
        String name = employeePositionDto.getEmployeePositionName();

        Optional<EmployeePosition> employeePositionOptional = employeePositionRepository
                .getEmployeePositionByName(name);

        if (name == null || name.length() <= 0){
            throw new EmployeePositionNameIsNullException();
        }

        if (employeePositionOptional.isPresent()){
            throw new EmployeePositionNameIsExistingException(name);
        }

        employeePositionRepository.insertEmployeePosition(
                employeePositionDto.getEmployeePositionName(),
                employeePositionDto.getIsActive());
    }

    public void inactivateEmployeePosition(EmployeePositionListDto employeePositionListDto){
        List<String> employeePositionNames = employeePositionListDto
                .getEmployeePositionListDto()
                .stream()
                .map((employeePositionDto) -> employeePositionDto.getEmployeePositionName())
                .collect(Collectors.toList());

        employeePositionRepository.inactivateEmployeePosition(employeePositionNames);
    }

    public void activateEmployeePosition(EmployeePositionListDto employeePositionListDto){
        List<String> employeePositionNames = employeePositionListDto
                .getEmployeePositionListDto()
                .stream()
                .map((employeePositionDto) -> employeePositionDto.getEmployeePositionName())
                .collect(Collectors.toList());

        employeePositionRepository.activateEmployeePosition(employeePositionNames);
    }

    public void updateEmployeePosition(EmployeePositionDto employeePositionDto, Long id) {

        EmployeePosition employeePosition = employeePositionRepository.getEmployeePositionById(id)
                .orElseThrow(() -> new EmployeePositionNotFoundException(id));

        String name = employeePositionDto.getEmployeePositionName();
        Boolean active = employeePositionDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new EmployeePositionNameIsNullException();
        }

        if (!Objects.equals(employeePosition.getEmployeePositionName(), name)) {

            Optional<EmployeePosition> employeePositionOptional = employeePositionRepository
                    .getEmployeePositionByName(name);

            if (employeePositionOptional.isPresent()){
                throw new EmployeePositionNameIsExistingException(name);
            }

            employeePosition.setEmployeePositionName(name);
        }

        employeePosition.setIsActive(active);
    }

}
