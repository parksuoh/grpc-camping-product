package com.example.product.applications;

import com.example.product.domains.FirstOptionName;
import com.example.product.domains.Money;
import com.example.product.domains.Product;
import com.example.product.domains.ProductFirstOption;
import com.example.product.domains.ProductSecondOption;
import com.example.product.domains.SecondOptionName;
import com.example.product.exceptions.ProductNotExist;
import com.example.product.repositories.ProductFirstOptionRepository;
import com.example.product.repositories.ProductRepository;
import com.example.product.repositories.ProductSecondOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddAdminProductFirstOptionService {

    private final ProductRepository productRepository;
    private final ProductFirstOptionRepository productFirstOptionRepository;
    private final ProductSecondOptionRepository productSecondOptionRepository;

    public AddAdminProductFirstOptionService(ProductRepository productRepository, ProductFirstOptionRepository productFirstOptionRepository, ProductSecondOptionRepository productSecondOptionRepository) {
        this.productRepository = productRepository;
        this.productFirstOptionRepository = productFirstOptionRepository;
        this.productSecondOptionRepository = productSecondOptionRepository;
    }

    public String addAdminProductFirstOption(Long productId, String name, Long price) {

        Product product = productRepository
                .findById(productId)
                .orElseThrow(ProductNotExist::new);

        ProductFirstOption productFirstOption = new ProductFirstOption(product, new FirstOptionName(name), new Money(price));

        productFirstOptionRepository.save(productFirstOption);

        ProductSecondOption productSecondOption = new ProductSecondOption(productFirstOption, new SecondOptionName("기본"), new Money(0L));
        productSecondOptionRepository.save(productSecondOption);

        return "success";
    }
}
