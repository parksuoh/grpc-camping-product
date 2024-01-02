package com.example.product.applications;

import com.example.product.applications.grpcclient.GrpcExistCategoryService;
import com.example.product.domains.Description;
import com.example.product.domains.Money;
import com.example.product.domains.Name;
import com.example.product.domains.Product;
import com.example.product.exceptions.CategoryNotExist;
import com.example.product.exceptions.ProductNotExist;
import com.example.product.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateProdcutService {

    private final ProductRepository productRepository;
    private final GrpcExistCategoryService grpcExistCategoryService;


    public UpdateProdcutService(ProductRepository productRepository, GrpcExistCategoryService grpcExistCategoryService) {
        this.productRepository = productRepository;
        this.grpcExistCategoryService = grpcExistCategoryService;
    }

    public String updateProduct(
            Long productId,
            Long categoryId,
            String name,
            Long price,
            String description
    ) {
        boolean isCategoryExist = grpcExistCategoryService.existCategory(categoryId);

        if (isCategoryExist) {
            throw new CategoryNotExist();
        }

        Product product = productRepository.findById(productId).orElseThrow(ProductNotExist::new);

        product.updateProduct(
                categoryId,
                new Name(name),
                new Money(price),
                new Description(description)
        );

        productRepository.save(product);

        return "success";
    }

}
