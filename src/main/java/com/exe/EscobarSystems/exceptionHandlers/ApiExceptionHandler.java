package com.exe.EscobarSystems.exceptionHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<Object> handleApiRequestException(RuntimeException e){
        ApiException apiException = new ApiException(
            e.getMessage(),
            HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }
}
