package com.jpa.common.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private String errorMessage;

    public ApiException(){}
    public ApiException(String message){
        super(message);
    }
}
