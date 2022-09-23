package com.exe.EscobarSystems.Supply;


import com.exe.EscobarSystems.Pagination.PaginationDto;
import com.exe.EscobarSystems.Supplier.Exceptions.SupplierNotFoundException;
import com.exe.EscobarSystems.Supplier.Supplier;
import com.exe.EscobarSystems.Supplier.SupplierDao;
import com.exe.EscobarSystems.Supply.Exceptions.*;
import com.exe.EscobarSystems.SupplyCategory.Exceptions.SupplyCategoryNotFoundException;
import com.exe.EscobarSystems.SupplyCategory.SupplyCategory;
import com.exe.EscobarSystems.SupplyCategory.SupplyCategoryDao;
import com.exe.EscobarSystems.UnitOfMeasurement.Exceptions.UnitOfMeasurementNotFoundException;
import com.exe.EscobarSystems.UnitOfMeasurement.UnitOfMeasurement;
import com.exe.EscobarSystems.UnitOfMeasurement.UnitOfMeasurementDao;
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
public class SupplyService {

    @Autowired
    @Qualifier("supply_mysql")
    SupplyDao supplyRepository;

    @Autowired
    @Qualifier("supplyCategory_mysql")
    SupplyCategoryDao supplyCategoryRepository;

    @Autowired
    @Qualifier("supplier_mysql")
    SupplierDao supplierRepository;

    @Autowired
    @Qualifier("unitOfMeasurement_mysql")
    UnitOfMeasurementDao unitOfMeasurementRepository;

    private SupplyDto convertEntityToDto(Supply supply){
        return new SupplyDto(
                supply.getSupplyId(),
                supply.getSupplyName(),
                supply.getSupplyQuantity(),
                supply.getMinimumQuantity(),
                supply.getInMinimumQuantity(),
                supply.getSupplier().getSupplierName(),
                supply.getUnitOfMeasurement().getUnitOfMeasurementAbbreviation(),
                supply.getSupplyCategory().getSupplyCategoryName(),
                supply.getIsActive()
        );
    }

    private SupplyDtoV2 convertEntityToDtoV2(Supply supply){
        return new SupplyDtoV2(
                supply.getSupplyName(),
                supply.getUnitOfMeasurement().getUnitOfMeasurementAbbreviation()
        );
    }

    private SupplyDtoV3 convertEntityToDtoV3(Supply supply){
        return new SupplyDtoV3(
                supply.getSupplyName(),
                supply.getUnitOfMeasurement().getUnitOfMeasurementAbbreviation(),
                supply.getSupplier().getSupplierName()
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
                return Sort.by("supply_name");

            case "Quantity":
                return Sort.by("supply_quantity");

            case "Minimum Quantity":
                return Sort.by("minimum_quantity");

            case "Supplier":
                return Sort.by("supplier.supplier_name");

            case "Unit of Measurement":
                return Sort.by("unit_of_measurement.unit_of_measurement_name");

            case "Supply Category":
                return Sort.by("supply_category.supply_category_name");

            case "None":
                return Sort.by("supply_id");

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

    private Map<String, Object> initializeSupplyWithPageDetails(Page<Supply> supplyPage, PaginationDto paginationDto){
        Integer pageNo = paginationDto.getPageNo();
        Integer totalPages = supplyPage.getTotalPages();
        Long totalCount = supplyPage.getTotalElements();

        Map<String, Object> supplyWithPageDetails = new HashMap<>();

        if (pageNo < 1 || pageNo > totalPages){
            supplyWithPageDetails.put("contents", new ArrayList<>());
            supplyWithPageDetails.put("totalPages", 0);
            supplyWithPageDetails.put("totalCount", 0);
            return supplyWithPageDetails;
        }

        supplyWithPageDetails.put("contents",
                supplyPage
                        .getContent()
                        .stream()
                        .map((Supply supply)-> convertEntityToDto(supply))
                        .collect(Collectors.toList()));

        supplyWithPageDetails.put("totalPages", totalPages);
        supplyWithPageDetails.put("totalCount", totalCount);



        return supplyWithPageDetails;
    }

    public List<String> getAllSupplyNames (){
        return supplyRepository.getAllPagedSupplies()
                .stream()
                .map((supply)-> supply.getSupplyName())
                .collect(Collectors.toList());
    }

    public Map<String, Object> getAllSupplies(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<Supply> supplyPage = supplyRepository
                .getAllPagedSupplies(pageable);


        return initializeSupplyWithPageDetails(supplyPage, paginationDto);
    }

    public List<SupplyDtoV2> getAllActiveSuppliesWithoutPagination(){
        return supplyRepository
                .getAllActiveSuppliesList()
                .stream()
                .map((Supply supply)-> convertEntityToDtoV2(supply))
                .collect(Collectors.toList());
    }

    public List<SupplyDtoV3> getAllActiveSuppliesWithSuppliersWithoutPagination(){
        return supplyRepository
                .getAllActiveSuppliesList()
                .stream()
                .map((Supply supply)-> convertEntityToDtoV3(supply))
                .collect(Collectors.toList());
    }

    public Map<String, Object> getAllActiveSupplies(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<Supply> supplyPage = supplyRepository
                .getAllActivePagedSupplies(pageable);


        return initializeSupplyWithPageDetails(supplyPage, paginationDto);
    }

    public Map<String, Object> getAllInactiveSupplies(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<Supply> supplyPage = supplyRepository
                .getAllInactivePagedSupplies(pageable);


        return initializeSupplyWithPageDetails(supplyPage, paginationDto);
    }

    public Map<String, Object> getAllActiveInMinimumSupplies(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<Supply> supplyPage = supplyRepository
                .getAllActiveInMinimumPagedSupplies(pageable);


        return initializeSupplyWithPageDetails(supplyPage, paginationDto);
    }

    public void validateSupply(String name,
                               Double minimumQuantity,
                               String supplierName,
                               String supplyCategoryName,
                               String unitOfMeasurementName){

        if (name == null || name.length() <= 0){
            throw new SupplyNameIsNullException();
        }

        if (minimumQuantity == null || minimumQuantity < 0){
            throw new SupplyMinimumQuantityIsLessThanZeroException();
        }

        if (supplierName == null || supplierName.length() <= 0){
            throw new SupplySupplierIsNullException();
        }

        if (supplyCategoryName == null || supplyCategoryName.length() <= 0){
            throw new SupplySupplyCategoryIsNullException();
        }

        if (unitOfMeasurementName == null || unitOfMeasurementName.length() <= 0){
            throw new SupplyUnitOfMeasurementIsNullException();
        }


    }

    public void addSupply(SupplyDto supplyDto){
        String name = supplyDto.getSupplyName();
        Double minimumQuantity = supplyDto.getMinimumQuantity();
        String supplierName = supplyDto.getSupplierName();
        String supplyCategoryName = supplyDto.getSupplyCategoryName();
        String unitOfMeasurementName = supplyDto.getUnitOfMeasurementName();

        Optional<Supply> supplyOptional = supplyRepository
                .getSupplyByName(name);

        validateSupply(name, minimumQuantity, supplierName, supplyCategoryName, unitOfMeasurementName);


        if (supplyOptional.isPresent()){
            throw new SupplyNameIsExistingException(name);
        }

        Supplier supplier = supplierRepository
                .getSupplierByName(supplierName)
                .orElseThrow(()->
                        new SupplierNotFoundException(supplierName));

        UnitOfMeasurement unitOfMeasurement = unitOfMeasurementRepository
                .getUnitOfMeasurementByName(unitOfMeasurementName)
                .orElseThrow(()->
                        new UnitOfMeasurementNotFoundException(unitOfMeasurementName));

        SupplyCategory supplyCategory = supplyCategoryRepository
                .getSupplyCategoryByName(supplyCategoryName)
                .orElseThrow(()->
                        new SupplyCategoryNotFoundException(supplyCategoryName));


        supplyRepository.insertSupply(
                supplyDto.getSupplyName(),
                supplyDto.getSupplyQuantity(),
                minimumQuantity,
                supplier.getSupplierId(),
                unitOfMeasurement.getUnitOfMeasurementId(),
                supplyCategory.getSupplyCategoryId(),
                supplyDto.getIsActive()
        );
    }

    public void inactivateSupply(SupplyListDto supplyListDto){
        List<String> supplyNames = supplyListDto
                .getSupplyListDto()
                .stream()
                .map((supplyDto) -> supplyDto.getSupplyName())
                .collect(Collectors.toList());

        supplyRepository.inactivateSupply(supplyNames);
    }

    public void activateSupply(SupplyListDto supplyListDto){
        List<String> supplyNames = supplyListDto
                .getSupplyListDto()
                .stream()
                .map((supplyDto) -> supplyDto.getSupplyName())
                .collect(Collectors.toList());

        supplyRepository.activateSupply(supplyNames);
    }

    public void updateSupply(SupplyDto supplyDto, Long id) {
        Supply supply = supplyRepository.getSupplyById(id)
                .orElseThrow(() -> new SupplyNotFoundException(id));

        String name = supplyDto.getSupplyName();
        Double quantity = supplyDto.getSupplyQuantity();
        Double minimumQuantity = supplyDto.getMinimumQuantity();
        String supplierName = supplyDto.getSupplierName();
        String supplyCategoryName = supplyDto.getSupplyCategoryName();
        String unitOfMeasurementName = supplyDto.getUnitOfMeasurementName();
        Boolean active = supplyDto.getIsActive();

        Supplier supplier = supplierRepository
                .getSupplierByName(supplyDto.getSupplierName())
                .orElseThrow(()->
                        new SupplierNotFoundException(supplyDto.getSupplierName()));

        UnitOfMeasurement unitOfMeasurement = unitOfMeasurementRepository
                .getUnitOfMeasurementByName(supplyDto.getUnitOfMeasurementName())
                .orElseThrow(()->
                        new UnitOfMeasurementNotFoundException(supplyDto.getUnitOfMeasurementName()));

        SupplyCategory supplyCategory = supplyCategoryRepository
                .getSupplyCategoryByName(supplyDto.getSupplyCategoryName())
                .orElseThrow(()->
                        new SupplyCategoryNotFoundException(supplyDto.getSupplyCategoryName()));

        validateSupply(name, minimumQuantity, supplierName, supplyCategoryName, unitOfMeasurementName);

        if (!Objects.equals(supply.getSupplyName(), name)){

            Optional<Supply> supplyOptional = supplyRepository
                    .getSupplyByName(name);

            if (supplyOptional.isPresent()){
                throw new SupplyNameIsExistingException(name);
            }

            supply.setSupplyName(name);
        }

        supply.setSupplyQuantity(quantity);
        supply.setMinimumQuantity(minimumQuantity);
        supply.setSupplier(supplier);
        supply.setUnitOfMeasurement(unitOfMeasurement);
        supply.setSupplyCategory(supplyCategory);
        supply.setIsActive(active);

    }
}
