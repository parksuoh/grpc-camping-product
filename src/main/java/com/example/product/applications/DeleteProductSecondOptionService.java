package com.example.product.applications;

import com.example.product.applications.grpcclient.GrpcDeleteCartItemBySecondOption;
import com.example.product.domains.ProductSecondOption;
import com.example.product.exceptions.ProductSecondOptionAtLeastOne;
import com.example.product.exceptions.ProductSecondOptionNotExist;
import com.example.product.repositories.ProductSecondOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DeleteProductSecondOptionService {

    private final ProductSecondOptionRepository productSecondOptionRepository;
    private final GrpcDeleteCartItemBySecondOption grpcDeleteCartItemBySecondOption;

    public DeleteProductSecondOptionService(ProductSecondOptionRepository productSecondOptionRepository, GrpcDeleteCartItemBySecondOption grpcDeleteCartItemBySecondOption) {
        this.productSecondOptionRepository = productSecondOptionRepository;
        this.grpcDeleteCartItemBySecondOption = grpcDeleteCartItemBySecondOption;
    }

    public String deleteProductSecondOption(Long ProdctSecondOptionId){

        ProductSecondOption productSecondOption = productSecondOptionRepository
                .findById(ProdctSecondOptionId)
                .orElseThrow(ProductSecondOptionNotExist::new);

        Long productFirstOptionId = productSecondOption.productFirstOption().id();

        List<ProductSecondOption> productFirstOptions = productSecondOptionRepository.findByProductFirstOption_Id(productFirstOptionId);

        if(productFirstOptions.size() <= 1){
            throw new ProductSecondOptionAtLeastOne();
        }

        productSecondOptionRepository.delete(productSecondOption);

        grpcDeleteCartItemBySecondOption.deleteCartItem(ProdctSecondOptionId);


        return "success";
    }

}