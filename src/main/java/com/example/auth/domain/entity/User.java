package com.example.auth.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

// 이서버는 로그인(토큰 발급) 회원가입(insert) 토큰검증(인가)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter @Table(name = "USERS")
public class User implements UserDetails {
    @Id @GeneratedValue
    @Column(name= "USER_ID")
    private UUID id;
    @Column(name="USER_EMAIL", unique = true)
    private String email;
    @Column(name="USER_PASSWORD")
    private String password;
    @Column(name="USER_NICKNAME")
    private String nickname;
    @Column(name = "USER_BIRTH_DAY")
    private LocalDate birthDay;
    @Column(name = "USER_GENDER")
    private String gender;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()-> "ROLE_USER");
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
