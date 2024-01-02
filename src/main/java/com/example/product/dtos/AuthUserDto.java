package com.example.product.dtos;

public record AuthUserDto(
        String name,
        String role,
        String accessToken
) {}
