package com.example.product.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3UploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        LocalDateTime localDateTime = LocalDateTime.now();

        Integer random = (int) (Math.random() * 10000);

        String changedFileName =  random.toString() + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, changedFileName, multipartFile.getInputStream(), metadata);
        return amazonS3.getUrl(bucket, changedFileName).toString();
    }
}