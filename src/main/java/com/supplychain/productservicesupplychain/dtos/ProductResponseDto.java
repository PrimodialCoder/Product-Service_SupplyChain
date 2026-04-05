package com.supplychain.productservicesupplychain.dtos;

import com.supplychain.productservicesupplychain.models.Product;
import com.supplychain.productservicesupplychain.services.ProductService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private long id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private String category;

    public static ProductResponseDto from(Product product) {
        ProductResponseDto productResponseDto = new ProductResponseDto();
        productResponseDto.setName(product.getName());
        productResponseDto.setPrice(product.getPrice());
        productResponseDto.setDescription(product.getDescription());
        productResponseDto.setImageUrl(product.getImageUrl());
        productResponseDto.setCategory(product.getCategory().getName());
        return productResponseDto;
    }
}
