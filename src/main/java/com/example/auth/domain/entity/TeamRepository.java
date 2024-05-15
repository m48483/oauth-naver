package com.example.auth.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository
    extends JpaRepository<Team, UUID> {
//    select * from Team where leader = ? and secret = ?
    Optional<Team> findByLeaderAndSecret(String leader, String secret);
}
