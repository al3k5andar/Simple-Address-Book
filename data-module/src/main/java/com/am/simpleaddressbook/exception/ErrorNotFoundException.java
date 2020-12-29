package com.am.simpleaddressbook.exception;

public class ErrorNotFoundException extends RuntimeException {

    public ErrorNotFoundException(){
        super();
    }

    public ErrorNotFoundException(String message){
        super(message);
    }

    public ErrorNotFoundException(String message, Throwable throwable){
        super(message, throwable);
    }
}
