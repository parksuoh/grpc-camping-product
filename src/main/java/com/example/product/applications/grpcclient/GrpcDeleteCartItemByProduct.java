package com.example.product.applications.grpcclient;

import com.example.grpc.DeleteCartItemByProductRequest;
import com.example.grpc.DeleteCartItemByProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcDeleteCartItemByProduct {

    private DeleteCartItemByProductServiceGrpc.DeleteCartItemByProductServiceBlockingStub deleteCartItemByProductServiceBlockingStub;
    private ManagedChannel channel;

    public GrpcDeleteCartItemByProduct(@Value("${cart.grpc.host}") String grpcHost, @Value("${cart.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    public void deleteCartItem(Long productId){
        DeleteCartItemByProductRequest deleteCartItemByProductRequest = DeleteCartItemByProductRequest.newBuilder()
                .setProductId(productId)
                .build();

        deleteCartItemByProductServiceBlockingStub = DeleteCartItemByProductServiceGrpc.newBlockingStub(channel);
        deleteCartItemByProductServiceBlockingStub.deleteCartItem(deleteCartItemByProductRequest);
    }

}
