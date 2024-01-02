package com.example.product.applications.grpcclient;


import com.example.grpc.DeleteCartItemByFirstItemRequest;
import com.example.grpc.DeleteCartItemByFirstOptionServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcDeleteCartItemByFirstOption {

    private DeleteCartItemByFirstOptionServiceGrpc.DeleteCartItemByFirstOptionServiceBlockingStub deleteCartItemByFirstOptionBlockingStub;
    private ManagedChannel channel;

    public GrpcDeleteCartItemByFirstOption(@Value("${cart.grpc.host}") String grpcHost, @Value("${cart.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    public void deleteCartItem(Long productFirstOptinId){
        DeleteCartItemByFirstItemRequest deleteCartItemByFirstItemRequest = DeleteCartItemByFirstItemRequest.newBuilder()
                .setFirstOptionId(productFirstOptinId)
                .build();

        deleteCartItemByFirstOptionBlockingStub = DeleteCartItemByFirstOptionServiceGrpc.newBlockingStub(channel);
        deleteCartItemByFirstOptionBlockingStub.deleteCartItem(deleteCartItemByFirstItemRequest);

    }

}
