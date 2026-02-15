package com.proglab.springboot_with_aws.services;

import java.io.InputStream;

public interface S3Service {

    String uploadFile(String fileName, InputStream inputStream,String contentType);

    String generatePresignedUrl(String fileName);

}
