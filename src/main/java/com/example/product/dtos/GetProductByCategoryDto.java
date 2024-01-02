package com.example.product.dtos;

import java.util.List;

public record GetProductByCategoryDto(
        Long id,
        String name,
        Long price,
        List<ProductImageDto> images
){}


