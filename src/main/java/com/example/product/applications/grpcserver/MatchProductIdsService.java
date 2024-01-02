package com.example.product.applications.grpcserver;

import com.example.grpc.MatchProductIdsRequest;
import com.example.grpc.MatchProductIdsResponse;
import com.example.grpc.MatchProductIdsServiceGrpc;
import com.example.product.domains.Product;
import com.example.product.domains.ProductFirstOption;
import com.example.product.domains.ProductSecondOption;
import com.example.product.repositories.ProductFirstOptionRepository;
import com.example.product.repositories.ProductRepository;
import com.example.product.repositories.ProductSecondOptionRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@GrpcService
@Transactional(readOnly = true)
public class MatchProductIdsService extends MatchProductIdsServiceGrpc.MatchProductIdsServiceImplBase {

    private final ProductRepository productRepository;
    private final ProductFirstOptionRepository productFirstOptionRepository;
    private final ProductSecondOptionRepository productSecondOptionRepository;

    public MatchProductIdsService(ProductRepository productRepository, ProductFirstOptionRepository productFirstOptionRepository, ProductSecondOptionRepository productSecondOptionRepository) {
        this.productRepository = productRepository;
        this.productFirstOptionRepository = productFirstOptionRepository;
        this.productSecondOptionRepository = productSecondOptionRepository;
    }

    @Override
    public void matchProductIds(MatchProductIdsRequest request, StreamObserver<MatchProductIdsResponse> responseObserver) {
        Boolean res;
        long productId = request.getProductId();
        long firstOptionId = request.getFirstOptionId();
        long secondOptionId = request.getSecondOptionId();

        Optional<Product> product = productRepository.findById(productId);
        Optional<ProductFirstOption> firstOption = productFirstOptionRepository.findById(firstOptionId);
        Optional<ProductSecondOption> secondOption = productSecondOptionRepository.findById(secondOptionId);

        if (!product.isPresent()
                || !firstOption.isPresent()
                || !secondOption.isPresent()
                || product.get().id() !=  firstOption.get().product().id()
                || firstOption.get().id() != secondOption.get().productFirstOption().id()){
            res = false;
        } else {
            res = true;
        }

        MatchProductIdsResponse reply = MatchProductIdsResponse.newBuilder()
                .setOk(res)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }
}
