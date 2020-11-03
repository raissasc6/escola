package com.example.demo.exception;

public class ResourceIllegalArgumentException  extends NullPointerException {

    public ResourceIllegalArgumentException (String error) {
        super(error);
    }
}
