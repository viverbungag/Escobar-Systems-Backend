package com.exe.EscobarSystems.exceptionHandlers;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
}
