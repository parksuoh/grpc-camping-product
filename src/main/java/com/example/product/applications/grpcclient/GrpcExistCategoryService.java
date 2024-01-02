package com.example.product.applications.grpcclient;

import com.example.grpc.ExistCategoryRequest;
import com.example.grpc.ExistCategoryResponse;
import com.example.grpc.ExistCategoryServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GrpcExistCategoryService {

    private ExistCategoryServiceGrpc.ExistCategoryServiceBlockingStub existCategoryServiceBlockingStub;
    private ManagedChannel channel;
    public GrpcExistCategoryService(@Value("${category.grpc.host}") String grpcHost, @Value("${category.grpc.port}") int grpcPort) {
        channel = ManagedChannelBuilder.forAddress(grpcHost, grpcPort)
                .usePlaintext()
                .build();
    }

    public boolean existCategory(Long categoryId) {

        ExistCategoryRequest existCategoryRequest = ExistCategoryRequest.newBuilder()
                .setCategoryId(categoryId)
                .build();

        existCategoryServiceBlockingStub = ExistCategoryServiceGrpc.newBlockingStub(channel);

        ExistCategoryResponse existCategoryResponse = existCategoryServiceBlockingStub.existCategory(existCategoryRequest);

        boolean result = existCategoryResponse.getResult();

        return result;
    }

}
