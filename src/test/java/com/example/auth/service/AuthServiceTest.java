package com.example.auth.service;

import com.example.auth.domain.entity.User;
import com.example.auth.domain.entity.UserRepository;
import com.example.auth.domain.request.SignInRequest;
import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.response.SignInResponse;
import com.example.auth.exception.ExistedUserException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Nested
    @Transactional
    class 로그인{
        @Test
        void 성공(){
            // given
            User init = User.builder().email("t@t.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("tt")
                    .gender("남")
                    .birthDay(LocalDate.of(1990, 1, 1))
                    .build();
            userRepository.save(init);
            SignInRequest request = new SignInRequest("t@t.com", "1234");

            // when
            SignInResponse res = authService.signIn(request);

            // then
            assertNotNull(res.token());
            assertEquals(3, res.token().split("\\.").length);
            assertEquals("Bearer", res.tokenType());
        }
        @Test
        void 실패_아이디가_틀릴때(){
            // given
            SignInRequest request = new SignInRequest("t@t.com", "1234");

            // when & then
            assertThrows(IllegalArgumentException.class, ()->authService.signIn(request));

        }
        @Test
        void 실패_비밀번호가_틀릴때(){
            // given
            User init = User.builder().email("t@t.com")
                    .password(passwordEncoder.encode("1234"))
                    .nickname("tt")
                    .gender("남")
                    .birthDay(LocalDate.of(1990, 1, 1))
                    .build();
            userRepository.save(init);
            SignInRequest request = new SignInRequest("t@t.com", "12342");
            // when & then
            assertThrows(IllegalArgumentException.class, ()->authService.signIn(request));
        }
    }
    @Nested
    @Transactional
    class 회원가입 {
        @Test
        void 성공(){
            // given
            SignUpRequest request = new SignUpRequest(
                    "a@b.com",
                    "1234",
                    "sss",
                    LocalDate.of(2000, 4, 7),
                    "남"
            );
            // when
            authService.insertUser(request);
            // then
            Optional<User> byEmail = userRepository.findByEmail(request.email());
            assertTrue(byEmail.isPresent());
            assertNotEquals("1234", byEmail.get().getPassword());

        }
        @Test
        void 실패_이미_있는_이메일(){
            // given
            SignUpRequest request = new SignUpRequest(
                    "a@b.com",
                    "1234",
                    "sss",
                    LocalDate.of(2000, 4, 7),
                    "남"
            );
            userRepository.save(User.builder().email("a@b.com").build());
            // when & then
            assertThrows(ExistedUserException.class, ()->{
                authService.insertUser(request);
            });
        }
    }
}