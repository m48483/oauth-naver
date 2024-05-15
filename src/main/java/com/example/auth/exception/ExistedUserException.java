package com.example.auth.exception;

public class ExistedUserException
    extends IllegalArgumentException{
    public ExistedUserException(String email) {
        super(email + " already exists");
    }
}
