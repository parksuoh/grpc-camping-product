package com.example.product.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record AddAdminFirstOptionRequestDto(
        @Positive
        Long productId,
        @NotBlank
        String name,
        @PositiveOrZero
        Long price
) {

}


