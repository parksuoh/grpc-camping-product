package com.example.product.exceptions;

public class ProductSecondOptionNotExist extends RuntimeException {

    public ProductSecondOptionNotExist() {
        super("상품 두번째 옵션이 존재하지 않습니다.");
    }
}
