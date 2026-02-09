package com.example.cashinvoice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<Map<String, String>> handleInvalidOrder(
            InvalidOrderException invalidOrderException ){
                return  new ResponseEntity<>(
                        Map.of("error", invalidOrderException.getMessage()),
                        HttpStatus.BAD_REQUEST
                );
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Map<String, String>>handleOrderNotFound(
            OrderNotFoundException orderNotFoundException){
        return  new ResponseEntity<>(
                Map.of("error", orderNotFoundException.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }



}
