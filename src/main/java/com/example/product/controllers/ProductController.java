package com.example.product.controllers;

import com.example.product.applications.GetProductDetailService;
import com.example.product.applications.GetProductsService;
import com.example.product.dtos.GetProductByCategoryDto;
import com.example.product.dtos.GetProductDetailDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/product/common")
public class ProductController {

    private final GetProductsService getProductsService;
    private final GetProductDetailService getProductDetailService;

    public ProductController(GetProductsService getProductsService, GetProductDetailService getProductDetailService) {
        this.getProductsService = getProductsService;
        this.getProductDetailService = getProductDetailService;
    }

    @GetMapping("/detail/{productId}")
    public GetProductDetailDto getDetail(@PathVariable Long productId) {

        return getProductDetailService.getProductDetail(productId);
    }

    @GetMapping("/{categoryId}")
    public List<GetProductByCategoryDto> get(@PathVariable Long categoryId) {

        return getProductsService.getProducts(categoryId);
    }
}
