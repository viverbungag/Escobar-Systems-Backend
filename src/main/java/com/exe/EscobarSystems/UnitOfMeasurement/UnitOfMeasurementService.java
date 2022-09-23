package com.exe.EscobarSystems.UnitOfMeasurement;

import com.exe.EscobarSystems.Pagination.PaginationDto;
import com.exe.EscobarSystems.UnitOfMeasurement.Exceptions.UnitOfMeasurementNameIsExistingException;
import com.exe.EscobarSystems.UnitOfMeasurement.Exceptions.UnitOfMeasurementNameIsNullException;
import com.exe.EscobarSystems.UnitOfMeasurement.Exceptions.UnitOfMeasurementNotFoundException;
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
public class UnitOfMeasurementService {

    @Autowired
    @Qualifier("unitOfMeasurement_mysql")
    UnitOfMeasurementDao unitOfMeasurementRepository;

    private UnitOfMeasurementDto convertEntityToDto(UnitOfMeasurement unitOfMeasurement){
        return new UnitOfMeasurementDto(
                unitOfMeasurement.getUnitOfMeasurementId(),
                unitOfMeasurement.getUnitOfMeasurementName(),
                unitOfMeasurement.getUnitOfMeasurementAbbreviation(),
                unitOfMeasurement.getIsActive()
        );
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
                return Sort.by("unit_of_measurement_name");
            case "Abbreviation":
                return Sort.by("unit_of_measurement_abbreviation");
            case "None":
                return Sort.by("unit_of_measurement_id");
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

    private Map<String, Object> initializeUnitOfMeasurementWithPageDetails(Page<UnitOfMeasurement> unitOfMeasurementPage, PaginationDto paginationDto){
        Integer pageNo = paginationDto.getPageNo();
        Integer totalPages = unitOfMeasurementPage.getTotalPages();
        Long totalCount = unitOfMeasurementPage.getTotalElements();

        Map<String, Object> unitOfMeasurementWithPageDetails = new HashMap<>();

        if (pageNo < 1 || pageNo > totalPages){
            unitOfMeasurementWithPageDetails.put("contents", new ArrayList<>());
            unitOfMeasurementWithPageDetails.put("totalPages", 0);
            unitOfMeasurementWithPageDetails.put("totalCount", 0);
            return unitOfMeasurementWithPageDetails;
        }

        unitOfMeasurementWithPageDetails.put("contents",
                unitOfMeasurementPage
                        .getContent()
                        .stream()
                        .map((UnitOfMeasurement unitOfMeasurement)-> convertEntityToDto(unitOfMeasurement))
                        .collect(Collectors.toList()));

        unitOfMeasurementWithPageDetails.put("totalPages", totalPages);
        unitOfMeasurementWithPageDetails.put("totalCount", totalCount);

        return unitOfMeasurementWithPageDetails;
    }

    public Map<String, Object> getAllUnitOfMeasurements(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<UnitOfMeasurement> unitOfMeasurementPage = unitOfMeasurementRepository
                .getAllUnitOfMeasurements(pageable);

        return initializeUnitOfMeasurementWithPageDetails(unitOfMeasurementPage, paginationDto);
    }

    public List<String> getAllActiveUnitOfMeasurementNames(){
        return unitOfMeasurementRepository
                .getAllActiveUnitOfMeasurementsList()
                .stream()
                .map((UnitOfMeasurement unitOfMeasurement)-> unitOfMeasurement.getUnitOfMeasurementName())
                .collect(Collectors.toList());
    }

    public Map<String, Object> getAllActiveUnitOfMeasurements(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<UnitOfMeasurement> unitOfMeasurementPage = unitOfMeasurementRepository
                .getAllActiveUnitOfMeasurements(pageable);

        return initializeUnitOfMeasurementWithPageDetails(unitOfMeasurementPage, paginationDto);
    }

    public Map<String, Object> getAllInactiveUnitOfMeasurements(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<UnitOfMeasurement> unitOfMeasurementPage = unitOfMeasurementRepository
                .getAllInactiveUnitOfMeasurements(pageable);

        return initializeUnitOfMeasurementWithPageDetails(unitOfMeasurementPage, paginationDto);
    }

    public void addUnitOfMeasurement(UnitOfMeasurementDto unitOfMeasurementDto) {
        String name = unitOfMeasurementDto.getUnitOfMeasurementName();

        Optional<UnitOfMeasurement> unitOfMeasurementOptional = unitOfMeasurementRepository
                .getUnitOfMeasurementByName(name);

        if (name == null || name.length() <= 0){
            throw new UnitOfMeasurementNameIsNullException();
        }

        if (unitOfMeasurementOptional.isPresent()){
            throw new UnitOfMeasurementNameIsExistingException(name);
        }

        unitOfMeasurementRepository.insertUnitOfMeasurement(
                unitOfMeasurementDto.getUnitOfMeasurementName(),
                unitOfMeasurementDto.getUnitOfMeasurementAbbreviation(),
                unitOfMeasurementDto.getIsActive());
    }

    public void inactivateUnitOfMeasurement(UnitOfMeasurementListDto unitOfMeasurementListDto){
        List<String> unitOfMeasurementNames = unitOfMeasurementListDto
                .getUnitOfMeasurementListDto()
                .stream()
                .map((unitOfMeasurementDto) -> unitOfMeasurementDto.getUnitOfMeasurementName())
                .collect(Collectors.toList());

        unitOfMeasurementRepository.inactivateUnitOfMeasurement(unitOfMeasurementNames);
    }

    public void activateUnitOfMeasurement(UnitOfMeasurementListDto unitOfMeasurementListDto){
        List<String> unitOfMeasurementNames = unitOfMeasurementListDto
                .getUnitOfMeasurementListDto()
                .stream()
                .map((unitOfMeasurementDto) -> unitOfMeasurementDto.getUnitOfMeasurementName())
                .collect(Collectors.toList());

        unitOfMeasurementRepository.activateUnitOfMeasurement(unitOfMeasurementNames);
    }

    public void updateUnitOfMeasurement(UnitOfMeasurementDto unitOfMeasurementDto, Long id) {

        UnitOfMeasurement unitOfMeasurement = unitOfMeasurementRepository.getUnitOfMeasurementById(id)
                .orElseThrow(() -> new UnitOfMeasurementNotFoundException(id));

        String name = unitOfMeasurementDto.getUnitOfMeasurementName();
        String abbreviation = unitOfMeasurementDto.getUnitOfMeasurementAbbreviation();
        Boolean active = unitOfMeasurementDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new UnitOfMeasurementNameIsNullException();
        }

        if (!Objects.equals(unitOfMeasurement.getUnitOfMeasurementName(), name)){

            Optional<UnitOfMeasurement> unitOfMeasurementOptional = unitOfMeasurementRepository
                    .getUnitOfMeasurementByName(name);

            if (unitOfMeasurementOptional.isPresent()){
                throw new UnitOfMeasurementNameIsExistingException(name);
            }

            unitOfMeasurement.setUnitOfMeasurementName(name);
        }

        unitOfMeasurement.setUnitOfMeasurementAbbreviation(abbreviation);
        unitOfMeasurement.setIsActive(active);
    }
}
