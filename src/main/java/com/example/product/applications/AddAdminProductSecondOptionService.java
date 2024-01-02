package com.example.product.applications;

import com.example.product.domains.Money;
import com.example.product.domains.ProductFirstOption;
import com.example.product.domains.ProductSecondOption;
import com.example.product.domains.SecondOptionName;
import com.example.product.exceptions.ProductFirstOptionNotExist;
import com.example.product.repositories.ProductFirstOptionRepository;
import com.example.product.repositories.ProductSecondOptionRepository;
import org.springframework.stereotype.Service;

@Service
public class AddAdminProductSecondOptionService {

    private final ProductFirstOptionRepository productFirstOptionRepository;
    private final ProductSecondOptionRepository productSecondOptionRepository;

    public AddAdminProductSecondOptionService(ProductFirstOptionRepository productFirstOptionRepository, ProductSecondOptionRepository productSecondOptionRepository) {
        this.productFirstOptionRepository = productFirstOptionRepository;
        this.productSecondOptionRepository = productSecondOptionRepository;
    }

    public String addAdminProductSecondOption(Long productFirstOptionId, String name, Long price) {

        ProductFirstOption productFirstOption = productFirstOptionRepository
                .findById(productFirstOptionId)
                .orElseThrow(ProductFirstOptionNotExist::new);

        ProductSecondOption productSecondOption = new ProductSecondOption(productFirstOption, new SecondOptionName(name), new Money(price));

        productSecondOptionRepository.save(productSecondOption);

        return "success";
    }
}
