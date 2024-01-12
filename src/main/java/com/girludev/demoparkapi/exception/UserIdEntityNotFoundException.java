package com.girludev.demoparkapi.exception;

public class UserIdEntityNotFoundException extends RuntimeException{
    public UserIdEntityNotFoundException(String message) {
    super(message);
    }
}
