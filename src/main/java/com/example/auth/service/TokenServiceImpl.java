package com.example.auth.service;

import com.example.auth.domain.entity.Team;
import com.example.auth.domain.entity.TeamRepository;
import com.example.auth.domain.entity.User;
import com.example.auth.domain.request.TeamRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
    private final TeamRepository teamRepository;
    private final JwtUtil jwtUtil;
    @Override
    public boolean isAuthenticatedTeam(TeamRequest request) {
        Optional<Team> byLeaderAndSecret = teamRepository
                .findByLeaderAndSecret(request.leader(), request.secret());
        byLeaderAndSecret.orElseThrow(IllegalArgumentException::new);
        return true;
    }

    @Override
    public SignInResponse refreshToken(User user) {
        return SignInResponse.from(jwtUtil.generateToken(user.getEmail()));
    }
}
