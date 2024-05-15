package com.example.auth.domain.request;

public record TeamRequest(
        String leader, String secret
) {
}
