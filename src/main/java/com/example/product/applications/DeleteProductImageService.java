package com.example.product.applications;

import com.example.product.aws.S3DeleteService;
import com.example.product.domains.ProductImage;
import com.example.product.exceptions.ProductImageNotExist;
import com.example.product.repositories.ProductImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class DeleteProductImageService {

    private final S3DeleteService s3DeleteService;

    private final ProductImageRepository productImageRepository;

    public DeleteProductImageService(S3DeleteService s3DeleteService, ProductImageRepository productImageRepository) {
        this.s3DeleteService = s3DeleteService;
        this.productImageRepository = productImageRepository;
    }

    public String deleteProductImage(Long productImageId) throws IOException {

        ProductImage productImage = productImageRepository
                .findById(productImageId)
                .orElseThrow(ProductImageNotExist::new);

        s3DeleteService.deleteFile(productImage.url());

        productImageRepository.delete(productImage);

        return "success";
    }
}