package com.example.auth.domain.request;


public record SignInRequest(
        String email,
        String password
){
}
