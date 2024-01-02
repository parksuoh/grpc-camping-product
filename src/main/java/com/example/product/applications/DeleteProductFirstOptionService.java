package com.example.product.applications;

import com.example.product.applications.grpcclient.GrpcDeleteCartItemByFirstOption;
import com.example.product.domains.ProductFirstOption;
import com.example.product.domains.ProductSecondOption;
import com.example.product.exceptions.ProductFirstOptionAtLeastOne;
import com.example.product.exceptions.ProductFirstOptionNotExist;
import com.example.product.repositories.ProductFirstOptionRepository;
import com.example.product.repositories.ProductSecondOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeleteProductFirstOptionService {

    private final ProductFirstOptionRepository productFirstOptionRepository;
    private final ProductSecondOptionRepository productSecondOptionRepository;
    private final GrpcDeleteCartItemByFirstOption grpcDeleteCartItemByFirstOption;

    public DeleteProductFirstOptionService(ProductFirstOptionRepository productFirstOptionRepository, ProductSecondOptionRepository productSecondOptionRepository, GrpcDeleteCartItemByFirstOption grpcDeleteCartItemByFirstOption) {
        this.productFirstOptionRepository = productFirstOptionRepository;
        this.productSecondOptionRepository = productSecondOptionRepository;
        this.grpcDeleteCartItemByFirstOption = grpcDeleteCartItemByFirstOption;
    }


    public String deleteProductFirstOption(Long ProdctFirstOptionId){

        ProductFirstOption productFirstOption = productFirstOptionRepository
                .findById(ProdctFirstOptionId)
                .orElseThrow(ProductFirstOptionNotExist::new);

        Long productId = productFirstOption.product().id();

        List<ProductFirstOption> productFirstOptions = productFirstOptionRepository.findByProduct_Id(productId);

        if(productFirstOptions.size() <= 1) {
            throw new ProductFirstOptionAtLeastOne();
        }

        productFirstOptionRepository.delete(productFirstOption);

        List<ProductSecondOption> productSecondOptions = productSecondOptionRepository.findByProductFirstOption_Id(ProdctFirstOptionId);

        for (ProductSecondOption productSecondOption : productSecondOptions) {
            productSecondOptionRepository.delete(productSecondOption);
        }

        grpcDeleteCartItemByFirstOption.deleteCartItem(ProdctFirstOptionId);


        return "success";
    }

}
