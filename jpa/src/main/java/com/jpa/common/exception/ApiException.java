package com.jpa.common.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    public ApiException(){}
    public ApiException(String message){
        super(message);
    }
}
