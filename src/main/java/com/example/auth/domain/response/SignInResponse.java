package com.example.auth.domain.response;

public record SignInResponse (
        String token,
        String tokenType
){
    public static SignInResponse from(String token){
        return new SignInResponse(token, "jwt");
    }
}
