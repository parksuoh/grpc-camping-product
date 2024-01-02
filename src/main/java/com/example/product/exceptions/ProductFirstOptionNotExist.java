package com.example.product.exceptions;

public class ProductFirstOptionNotExist extends RuntimeException {

    public ProductFirstOptionNotExist() {
        super("상품 첫번째 옵션이 존재하지 않습니다.");
    }
}
