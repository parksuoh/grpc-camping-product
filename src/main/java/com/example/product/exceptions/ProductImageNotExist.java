package com.example.product.exceptions;

public class ProductImageNotExist extends RuntimeException {

    public ProductImageNotExist() {
        super("상품 이미지가 존재하지 않습니다.");
    }
}
