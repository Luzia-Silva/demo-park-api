package com.girludev.demoparkapi.exception;

public class CustomerIdEntityNotFoundException extends  RuntimeException{
    public CustomerIdEntityNotFoundException(String message)  {
        super(message);
    }
}
