package com.proglab.springboot_with_aws.controller;

import com.proglab.springboot_with_aws.model.Product;
import com.proglab.springboot_with_aws.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Double price,
            @RequestParam(required = false)MultipartFile file
            ){

        Product product = productService.addProduct(name, description, price, file);
        return ResponseEntity.ok(product);
    }


    @GetMapping
    public ResponseEntity<List<Product>> ListOfProduct(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

}





