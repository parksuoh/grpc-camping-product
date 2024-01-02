package com.example.product.applications;

import com.example.product.applications.grpcclient.GrpcExistCategoryService;
import com.example.product.aws.S3UploadService;
import com.example.product.domains.Description;
import com.example.product.domains.FirstOptionName;
import com.example.product.domains.Money;
import com.example.product.domains.Name;
import com.example.product.domains.Product;
import com.example.product.domains.ProductFirstOption;
import com.example.product.domains.ProductImage;
import com.example.product.domains.ProductSecondOption;
import com.example.product.domains.SecondOptionName;
import com.example.product.exceptions.CategoryNotExist;
import com.example.product.repositories.ProductFirstOptionRepository;
import com.example.product.repositories.ProductImageRepository;
import com.example.product.repositories.ProductRepository;
import com.example.product.repositories.ProductSecondOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
public class AddProductService {

    private final S3UploadService s3UploadService;
    private final ProductRepository productRepository;
    private final ProductFirstOptionRepository productFirstOptionRepository;
    private final ProductSecondOptionRepository productSecondOptionRepository;
    private final ProductImageRepository productImageRepository;

    private final GrpcExistCategoryService grpcExistCategoryService;

    public AddProductService(S3UploadService s3UploadService, ProductRepository productRepository, ProductFirstOptionRepository productFirstOptionRepository, ProductSecondOptionRepository productSecondOptionRepository, ProductImageRepository productImageRepository, GrpcExistCategoryService grpcExistCategoryService) {
        this.s3UploadService = s3UploadService;
        this.productRepository = productRepository;
        this.productFirstOptionRepository = productFirstOptionRepository;
        this.productSecondOptionRepository = productSecondOptionRepository;
        this.productImageRepository = productImageRepository;
        this.grpcExistCategoryService = grpcExistCategoryService;
    }

    public String addProduct(
            Long categoryId,
            String name,
            Long price,
            String description,
            MultipartFile image
    ) throws IOException {

        String url = s3UploadService.saveFile(image);

        boolean isCategoryExist = grpcExistCategoryService.existCategory(categoryId);

        if (!isCategoryExist) {
            throw new CategoryNotExist();
        }

        Product product = new Product(categoryId, new Name(name), new Money(price), new Description(description));
        productRepository.save(product);

        ProductImage newProductImage = new ProductImage(product, url);
        productImageRepository.save(newProductImage);

        ProductFirstOption productFirstOption = new ProductFirstOption(product, new FirstOptionName("기본"), new Money(0L));
        productFirstOptionRepository.save(productFirstOption);

        ProductSecondOption productSecondOption = new ProductSecondOption(productFirstOption, new SecondOptionName("기본"), new Money(0L));
        productSecondOptionRepository.save(productSecondOption);

        return "success";
    }

}
