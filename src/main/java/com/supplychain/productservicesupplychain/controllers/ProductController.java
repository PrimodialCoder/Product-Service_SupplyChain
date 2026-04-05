package com.supplychain.productservicesupplychain.controllers;

import com.supplychain.productservicesupplychain.dtos.ProductResponseDto;
import com.supplychain.productservicesupplychain.models.Product;
import com.supplychain.productservicesupplychain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);
        return ProductResponseDto.from(product);
    }
}
