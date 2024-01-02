package com.example.product.exceptions;

public class ProductFirstOptionAtLeastOne extends RuntimeException {

    public ProductFirstOptionAtLeastOne() {
        super("상품 첫번째 옵션이 최소 한개는 존재하여야 합니다.");
    }
}
