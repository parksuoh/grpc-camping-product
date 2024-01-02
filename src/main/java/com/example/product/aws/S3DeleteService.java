package com.example.product.aws;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class S3DeleteService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3DeleteService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void deleteFile(String url) throws IOException {
        String splitStr = ".com/";
        String fileName = url.substring(url.lastIndexOf(splitStr) + splitStr.length());

        amazonS3.deleteObject(bucket, fileName);
    }
}