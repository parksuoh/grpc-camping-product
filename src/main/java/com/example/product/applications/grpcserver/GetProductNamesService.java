package com.example.product.applications.grpcserver;

import com.example.grpc.GetProductNamesRequest;
import com.example.grpc.GetProductNamesResponse;
import com.example.grpc.GetProductNamesServiceGrpc;
import com.example.product.domains.Product;
import com.example.product.domains.ProductFirstOption;
import com.example.product.domains.ProductSecondOption;
import com.example.product.repositories.ProductFirstOptionRepository;
import com.example.product.repositories.ProductRepository;
import com.example.product.repositories.ProductSecondOptionRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@GrpcService
public class GetProductNamesService extends GetProductNamesServiceGrpc.GetProductNamesServiceImplBase {

    private final ProductRepository productRepository;
    private final ProductFirstOptionRepository productFirstOptionRepository;
    private final ProductSecondOptionRepository productSecondOptionRepository;


    public GetProductNamesService(ProductRepository productRepository, ProductFirstOptionRepository productFirstOptionRepository, ProductSecondOptionRepository productSecondOptionRepository) {
        this.productRepository = productRepository;
        this.productFirstOptionRepository = productFirstOptionRepository;
        this.productSecondOptionRepository = productSecondOptionRepository;
    }


    @Override
    public void getProductNames(GetProductNamesRequest request, StreamObserver<GetProductNamesResponse> responseObserver) {
        GetProductNamesResponse reply;
        long productId = request.getProductId();
        long firstOptionId = request.getFirstOptionId();
        long secondOptionId = request.getSecondOptionId();

        Optional<Product> product = productRepository.findById(productId);
        Optional<ProductFirstOption> firstOption = productFirstOptionRepository.findById(firstOptionId);
        Optional<ProductSecondOption> secondOption = productSecondOptionRepository.findById(secondOptionId);

        if (!product.isPresent() || !firstOption.isPresent() || !secondOption.isPresent()){
            reply = GetProductNamesResponse.newBuilder()
                    .setProductName("null")
                    .setProductPrice(0L)
                    .setFirstOptionName("null")
                    .setFirstOptionPrice(0L)
                    .setSecondOptionName("null")
                    .setSecondOptionPrice(0L)
                    .build();
        } else {
            reply = GetProductNamesResponse.newBuilder()
                    .setProductName(product.get().name().toString())
                    .setProductPrice(product.get().price().asLong())
                    .setFirstOptionName(firstOption.get().name().toString())
                    .setFirstOptionPrice(firstOption.get().addPrice().asLong())
                    .setSecondOptionName(secondOption.get().name().toString())
                    .setSecondOptionPrice(secondOption.get().addPrice().asLong())
                    .build();
        }

        responseObserver.onNext(reply);
        responseObserver.onCompleted();

    }
}
