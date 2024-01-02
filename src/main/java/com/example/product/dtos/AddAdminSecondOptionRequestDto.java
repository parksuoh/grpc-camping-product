package com.example.product.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record AddAdminSecondOptionRequestDto(
        @Positive
        Long productFirstOptionId,
        @NotBlank
        String name,
        @PositiveOrZero
        Long price
){}

