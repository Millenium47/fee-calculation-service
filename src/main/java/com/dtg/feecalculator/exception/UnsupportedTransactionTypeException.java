package com.dtg.feecalculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UnsupportedTransactionTypeException extends RuntimeException {
    public UnsupportedTransactionTypeException(){
        super();
    }

    public UnsupportedTransactionTypeException(String message){
        super(message);
    }

    public UnsupportedTransactionTypeException(String message, Throwable cause){
        super(message, cause);
    }
}
