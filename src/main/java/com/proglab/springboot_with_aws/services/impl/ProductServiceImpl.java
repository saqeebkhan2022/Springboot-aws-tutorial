package com.proglab.springboot_with_aws.services.impl;

import com.proglab.springboot_with_aws.model.Product;
import com.proglab.springboot_with_aws.repository.ProductRepository;
import com.proglab.springboot_with_aws.services.ProductService;
import com.proglab.springboot_with_aws.services.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final S3Service s3Service;

    @Override
    public Product addProduct(String name, String description, Double price, MultipartFile file) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        if (file != null && !file.isEmpty()){
            try{

                String fileUrl = s3Service.uploadFile(file.getOriginalFilename()
                        , file.getInputStream(), file.getContentType());
                product.setImageUrl(fileUrl);
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload product image",e);
            }
        }

        return productRepository.save(product);

    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> {
            if (product.getImageUrl() != null){
                String url = s3Service.generatePresignedUrl(product.getImageUrl());
                product.setImageUrl(url);
            }
        });
        return products;
    }
}
