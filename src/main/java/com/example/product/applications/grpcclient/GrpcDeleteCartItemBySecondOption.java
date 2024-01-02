package com.example.product.applications.grpcclient;

import com.example.grpc.DeleteCartItemBySecondItemRequest;
import com.example.grpc.DeleteCartItemBySecondOptionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcDeleteCartItemBySecondOption {

    private DeleteCartItemBySecondOptionServiceGrpc.DeleteCartItemBySecondOptionServiceBlockingStub deleteCartItemBySecondOptionBlockingStub;
    private ManagedChannel channel;

    public GrpcDeleteCartItemBySecondOption(@Value("${cart.grpc.host}") String grpcHost, @Value("${cart.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    public void deleteCartItem(Long productSecondOptinId){
        DeleteCartItemBySecondItemRequest deleteCartItemBySecondItemRequest = DeleteCartItemBySecondItemRequest.newBuilder()
                .setSecondOptionId(productSecondOptinId)
                .build();

        deleteCartItemBySecondOptionBlockingStub = DeleteCartItemBySecondOptionServiceGrpc.newBlockingStub(channel);

        deleteCartItemBySecondOptionBlockingStub.deleteCartItem(deleteCartItemBySecondItemRequest);

    }

}
