package com.supplychain.productservicesupplychain.controllers;

import com.supplychain.productservicesupplychain.dtos.CreateFakeStoreProductRequestDto;
import com.supplychain.productservicesupplychain.dtos.ErrorDto;
import com.supplychain.productservicesupplychain.dtos.ProductResponseDto;
import com.supplychain.productservicesupplychain.exceptions.ProductNotFoundException;
import com.supplychain.productservicesupplychain.models.Product;
import com.supplychain.productservicesupplychain.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}")
    public ProductResponseDto getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return ProductResponseDto.from(product);
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts() throws ProductNotFoundException {
        List<Product> allProducts = productService.getAllProducts();
        return allProducts.stream().map(ProductResponseDto::from).collect(Collectors.toList());
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestBody CreateFakeStoreProductRequestDto
                    requestDto) throws ProductNotFoundException {
        Product product = productService.createProduct(
                requestDto.getName(),
                requestDto.getDescription(),
                requestDto.getPrice(),
                requestDto.getImageUrl(),
                requestDto.getCategory()
        );
        return new ResponseEntity<>(ProductResponseDto.from(product),HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ProductResponseDto> replaceProduct(@PathVariable("id") long id, @RequestBody CreateFakeStoreProductRequestDto requestDto) throws ProductNotFoundException {
        Product updatedProduct = productService.replaceProduct(
                id,
                requestDto.getName(),
                requestDto.getDescription(),
                requestDto.getPrice(),
                requestDto.getImageUrl(),
                requestDto.getCategory()
        );
        return new ResponseEntity<>(ProductResponseDto.from(updatedProduct),HttpStatus.OK);

    }


//    @ExceptionHandler(NullPointerException.class)
//    public ErrorDto handleNullPointerException(){
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setStatus("Failure");
//        errorDto.setMessage("Product Cannot be null");
//        return errorDto;
//    }

}
