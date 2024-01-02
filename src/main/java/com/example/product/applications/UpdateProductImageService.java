package com.example.product.applications;

import com.example.product.aws.S3DeleteService;
import com.example.product.aws.S3UploadService;
import com.example.product.domains.Product;
import com.example.product.domains.ProductImage;
import com.example.product.exceptions.ProductImageNotExist;
import com.example.product.exceptions.ProductNotExist;
import com.example.product.repositories.ProductImageRepository;
import com.example.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class UpdateProductImageService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    private final S3UploadService s3UploadService;

    private final S3DeleteService s3DeleteService;

    public UpdateProductImageService(ProductRepository productRepository, ProductImageRepository productImageRepository, S3UploadService s3UploadService, S3DeleteService s3DeleteService) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.s3UploadService = s3UploadService;
        this.s3DeleteService = s3DeleteService;
    }

    public String updateProductImageService(Long productId, Long productImageId, MultipartFile image) throws IOException {

        ProductImage productImage = productImageRepository
                .findById(productImageId)
                .orElseThrow(ProductImageNotExist::new);

        s3DeleteService.deleteFile(productImage.url());

        productImageRepository.delete(productImage);


        Product product = productRepository
                .findById(productId)
                .orElseThrow(ProductNotExist::new);

        String url = s3UploadService.saveFile(image);

        ProductImage newProductImage = new ProductImage(product, url);

        productImageRepository.save(newProductImage);

        return "success";
    }
}
