package com.exe.EscobarSystems.Pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaginationDto {

    private int pageNo;
    private int pageSize;
    private String sortedBy;
    private Boolean isAscending;
}
