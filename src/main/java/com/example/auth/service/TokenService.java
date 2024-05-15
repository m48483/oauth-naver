package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.TeamRequest;
import com.example.auth.domain.response.SignInResponse;

public interface TokenService {
    boolean isAuthenticatedTeam(TeamRequest request);
    SignInResponse refreshToken(User user);
}
