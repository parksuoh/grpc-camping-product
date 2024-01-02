package com.example.product.dtos;

import java.util.List;

public record GetProductFitstOptionDto(
        Long id,
        String name,
        Long addPrice,
        List<GetProductSecondOptionDto> secondOptions
){}

