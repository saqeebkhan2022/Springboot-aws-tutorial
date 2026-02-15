package com.proglab.springboot_with_aws.services;

import com.proglab.springboot_with_aws.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Product addProduct(String name, String description, Double price, MultipartFile file);

    List<Product> getAllProducts();
}
