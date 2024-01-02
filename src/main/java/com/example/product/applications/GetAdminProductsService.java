package com.example.product.applications;

import com.example.product.domains.Product;
import com.example.product.domains.ProductImage;
import com.example.product.dtos.GetProductByCategoryDto;
import com.example.product.dtos.ProductImageDto;
import com.example.product.repositories.ProductImageRepository;
import com.example.product.repositories.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GetAdminProductsService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public GetAdminProductsService(ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    public List<GetProductByCategoryDto> getAdminProducts() {

        List<Product> products = productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        return products
                .stream()
                .map(product -> {
                    List<ProductImage> images = productImageRepository.findByProduct_Id(product.id());

                    return new GetProductByCategoryDto(
                            product.id(),
                            product.name().toString(),
                            product.price().asLong(),
                            imagesToDto(images)
                    );
                })
                .toList();
    }


    private List<ProductImageDto> imagesToDto(List<ProductImage> images) {

        return images
                .stream()
                .map(productImage -> new ProductImageDto(productImage.id(), productImage.url()))
                .toList();

    }
}
