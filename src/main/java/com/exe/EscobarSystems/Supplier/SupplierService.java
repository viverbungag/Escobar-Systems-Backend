package com.exe.EscobarSystems.Supplier;


import com.exe.EscobarSystems.Pagination.PaginationDto;
import com.exe.EscobarSystems.Supplier.Exceptions.SupplierNameIsExistingException;
import com.exe.EscobarSystems.Supplier.Exceptions.SupplierNameIsNullException;
import com.exe.EscobarSystems.Supplier.Exceptions.SupplierNotFoundException;
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
public class SupplierService {

    @Autowired
    @Qualifier("supplier_mysql")
    SupplierDao supplierRepository;

    private SupplierDto convertEntityToDto(Supplier supplier){
        return new SupplierDto(
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getSupplierAddress(),
                supplier.getSupplierContactNumber(),
                supplier.getSupplierContactPerson(),
                supplier.getIsActive());
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
                return Sort.by("supplier_name");
            case "Address":
                return Sort.by("supplier_address");
            case "Contact Number":
                return Sort.by("supplier_contact_number");
            case "Contact Person":
                return Sort.by("supplier_contact_person");
            case "None":
                return Sort.by("supplier_id");
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

    private Map<String, Object> initializeSupplierWithPageDetails(Page<Supplier> supplierPage, PaginationDto paginationDto){
        Integer pageNo = paginationDto.getPageNo();
        Integer totalPages = supplierPage.getTotalPages();
        Long totalCount = supplierPage.getTotalElements();

        Map<String, Object> supplierWithPageDetails = new HashMap<>();

        if (pageNo < 1 || pageNo > totalPages){
            supplierWithPageDetails.put("contents", new ArrayList<>());
            supplierWithPageDetails.put("totalPages", 0);
            supplierWithPageDetails.put("totalCount", 0);
            return supplierWithPageDetails;
        }

        supplierWithPageDetails.put("contents",
                supplierPage
                        .getContent()
                        .stream()
                        .map((Supplier supplier)-> convertEntityToDto(supplier))
                        .collect(Collectors.toList()));

        supplierWithPageDetails.put("totalPages", totalPages);
        supplierWithPageDetails.put("totalCount", totalCount);

        return supplierWithPageDetails;
    }


    public Map<String, Object> getAllSuppliers(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<Supplier> supplierPage = supplierRepository
                .getAllSuppliers(pageable);

        return initializeSupplierWithPageDetails(supplierPage, paginationDto);
    }

    public List<String> getAllActiveSupplierNames(){
        return supplierRepository
                .getAllActiveSupplierList()
                .stream()
                .map((Supplier supplier)-> supplier.getSupplierName())
                .collect(Collectors.toList());
    }

    public Map<String, Object> getAllActiveSuppliers(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<Supplier> supplierPage = supplierRepository
                .getAllActiveSuppliers(pageable);

        return initializeSupplierWithPageDetails(supplierPage, paginationDto);
    }

    public Map<String, Object> getAllInactiveSuppliers(PaginationDto paginationDto) {
        Pageable pageable = initializePageable(paginationDto);
        Page<Supplier> supplierPage = supplierRepository
                .getAllInactiveSuppliers(pageable);

        return initializeSupplierWithPageDetails(supplierPage, paginationDto);
    }

    public void inactivateSupplier(SupplierListDto supplierListDto){
        List<String> supplierNames = supplierListDto
                .getSupplierListDto()
                .stream()
                .map((supplierDto) -> supplierDto.getSupplierName())
                .collect(Collectors.toList());

        supplierRepository.inactivateSupplier(supplierNames);
    }
    public void activateSupplier(SupplierListDto supplierListDto){
        List<String> supplierNames = supplierListDto
                .getSupplierListDto()
                .stream()
                .map((supplierDto) -> supplierDto.getSupplierName())
                .collect(Collectors.toList());

        supplierRepository.activateSupplier(supplierNames);
    }


    public void addSupplier(SupplierDto supplierDto) {
        String name = supplierDto.getSupplierName();

        Optional<Supplier> supplierOptional =  supplierRepository
                .getSupplierByName(name);

        if (name == null || name.length() <= 0){
            throw new SupplierNameIsNullException();
        }

        if (supplierOptional.isPresent()){
            throw new SupplierNameIsExistingException(name);
        }

        supplierRepository.insertSupplier(
                supplierDto.getSupplierName(),
                supplierDto.getSupplierAddress(),
                supplierDto.getSupplierContactNumber(),
                supplierDto.getSupplierContactPerson(),
                supplierDto.getIsActive());
    }

    public void updateSupplier(SupplierDto supplierDto, Long id) {

        Supplier supplier = supplierRepository.getSupplierById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id));

        String name = supplierDto.getSupplierName();
        String address = supplierDto.getSupplierAddress();
        String contactNumber = supplierDto.getSupplierContactNumber();
        String contactPerson = supplierDto.getSupplierContactPerson();
        Boolean active = supplierDto.getIsActive();

        if (name == null || name.length() <= 0){
            throw new SupplierNameIsNullException();
        }

        if (!Objects.equals(supplier.getSupplierName(), name)){

            Optional<Supplier> supplierOptional =  supplierRepository
                    .getSupplierByName(name);

            if (supplierOptional.isPresent()){
                throw new SupplierNameIsExistingException(name);
            }

            supplier.setSupplierName(name);
        }

        supplier.setSupplierAddress(address);
        supplier.setSupplierContactNumber(contactNumber);
        supplier.setSupplierContactPerson(contactPerson);
        supplier.setIsActive(active);

    }
}
