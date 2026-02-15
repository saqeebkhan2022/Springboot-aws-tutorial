package com.proglab.springboot_with_aws.services.impl;

import com.proglab.springboot_with_aws.services.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3ServiceImpl(S3Client s3Client, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
    }

    @Override
    public String uploadFile(String fileName, InputStream inputStream, String contentType) {

        try{
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(contentType)
                    .build();

//            s3Client.putObject(putObjectRequest,
//                    RequestBody.fromInputStream(inputStream, inputStream.available()));
//            return "https://"+bucketName+".s3.amazonaws.com/"+fileName;
            s3Client.putObject(putObjectRequest, RequestBody
                    .fromBytes(inputStream.readAllBytes()));
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file");
        }


    }

    @Override
    public String generatePresignedUrl(String fileName) {
       GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
       PresignedGetObjectRequest objectRequest =  s3Presigner.presignGetObject(p -> p
                .signatureDuration(Duration.ofMinutes(10))
                .getObjectRequest(getObjectRequest));
        return objectRequest.url().toString();
    }


}
