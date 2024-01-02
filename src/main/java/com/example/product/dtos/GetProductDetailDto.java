package com.example.product.dtos;

import java.util.List;

public record GetProductDetailDto(
        Long id,
        Long categoryId,
        String name,
        Long price,
        String description,
        List<ProductImageDto> images,
        List<GetProductFitstOptionDto> firstOptions
) {}


