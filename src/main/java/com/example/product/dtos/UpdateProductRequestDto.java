package com.example.product.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateProductRequestDto(
        @Positive
        Long productId,

        @Positive
        Long categoryId,
        @NotBlank
        String name,
        @PositiveOrZero
        Long price,
        @NotNull
        String description
){}


