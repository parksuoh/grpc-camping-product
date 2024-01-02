package com.example.product.exceptions;

public class ProductSecondOptionAtLeastOne extends RuntimeException {

    public ProductSecondOptionAtLeastOne() {
        super("상품 두번째 옵션이 최소 한개는 존재하여야 합니다.");
    }
}
