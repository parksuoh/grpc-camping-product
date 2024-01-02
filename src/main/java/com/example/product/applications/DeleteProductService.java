package com.example.product.applications;

import com.example.product.applications.grpcclient.GrpcDeleteCartItemByProduct;
import com.example.product.aws.S3DeleteService;
import com.example.product.domains.Product;
import com.example.product.domains.ProductFirstOption;
import com.example.product.domains.ProductImage;
import com.example.product.domains.ProductSecondOption;
import com.example.product.exceptions.ProductNotExist;
import com.example.product.repositories.ProductFirstOptionRepository;
import com.example.product.repositories.ProductImageRepository;
import com.example.product.repositories.ProductRepository;
import com.example.product.repositories.ProductSecondOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class DeleteProductService {

    private final ProductRepository productRepository;
    private final ProductFirstOptionRepository productFirstOptionRepository;
    private final ProductSecondOptionRepository productSecondOptionRepository;
    private final ProductImageRepository productImageRepository;
    private final S3DeleteService s3DeleteService;
    private final GrpcDeleteCartItemByProduct grpcDeleteCartItemByProduct;


    public DeleteProductService(ProductRepository productRepository, ProductFirstOptionRepository productFirstOptionRepository, ProductSecondOptionRepository productSecondOptionRepository, ProductImageRepository productImageRepository, S3DeleteService s3DeleteService, GrpcDeleteCartItemByProduct grpcDeleteCartItemByProduct) {
        this.productRepository = productRepository;
        this.productFirstOptionRepository = productFirstOptionRepository;
        this.productSecondOptionRepository = productSecondOptionRepository;
        this.productImageRepository = productImageRepository;
        this.s3DeleteService = s3DeleteService;
        this.grpcDeleteCartItemByProduct = grpcDeleteCartItemByProduct;
    }

    public String deleteProduct(Long productId) throws IOException {

        Product product = productRepository.findById(productId).orElseThrow(ProductNotExist::new);
        List<ProductFirstOption> productFirstOptions = productFirstOptionRepository.findByProduct_Id(productId);
        List<ProductImage> productImages = productImageRepository.findByProduct_Id(productId);

        deleteFirstOptions(productFirstOptions);
        deleteProductImages(productImages);
        grpcDeleteCartItemByProduct.deleteCartItem(productId);
        productRepository.delete(product);

        return "success";
    }


    private void deleteFirstOptions(List<ProductFirstOption> productFirstOptions) {
        for (ProductFirstOption productFirstOption : productFirstOptions) {
            List<ProductSecondOption> productSecondOptions = productSecondOptionRepository.findByProductFirstOption_Id(productFirstOption.id());

            deleteSecondOptions(productSecondOptions);

            productFirstOptionRepository.delete(productFirstOption);

        }
    }

    private void deleteSecondOptions(List<ProductSecondOption> productSecondOptions) {
        for (ProductSecondOption productSecondOption : productSecondOptions) {
            productSecondOptionRepository.delete(productSecondOption);
        }

    }

    private void deleteProductImages(List<ProductImage> productImages) throws IOException {
        for (ProductImage productImage : productImages) {
            s3DeleteService.deleteFile(productImage.url());
            productImageRepository.delete(productImage);
        }
    }


}
