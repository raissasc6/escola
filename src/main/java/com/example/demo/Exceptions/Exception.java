package com.example.demo.Exceptions;

public class Exception  extends java.lang.Exception{
    public static class NotFoundException extends java.lang.Exception {
        public NotFoundException(String message) {
            super(message);
        }
    }
}
