package com.example.demo.exception;

public class ResourceNotFoundException extends NullPointerException {
    public ResourceNotFoundException(String error) {
        super(error);
    }
}
//Não precisa throw