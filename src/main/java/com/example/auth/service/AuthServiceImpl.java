package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.exception.ExistedUserException;
import com.example.auth.global.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository
                .findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
    @Override
    public void insertUser(SignUpRequest request) {
        log.info("insert user");
        Optional<User> byEmail = userRepository.findByEmail(request.email());
        if(byEmail.isPresent()) throw new ExistedUserException(request.email());
        String encodedPassword = passwordEncoder.encode(request.password());
        User user = request.toEntity(encodedPassword);
        // log
        // error < info < debug < trace
        userRepository.save(user);
    }
    @Override
    public SignInResponse signIn(SignInRequest request) {
        Optional<User> byEmail = userRepository.findByEmail(request.email());
        if(byEmail.isEmpty() ||
                !passwordEncoder.matches(request.password(), byEmail.get().getPassword()))
            throw new IllegalArgumentException();
        String token = jwtUtil.generateToken(request.email());
        return SignInResponse.from(token);
    }


}
