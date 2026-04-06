package com.supplychain.productservicesupplychain.services;

import com.supplychain.productservicesupplychain.dtos.FakeStoreResponseDto;
import com.supplychain.productservicesupplychain.exceptions.ProductNotFoundException;
import com.supplychain.productservicesupplychain.models.Category;
import com.supplychain.productservicesupplychain.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreResponseDto.class, id);
        if(fakeStoreResponseDto == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return fakeStoreResponseDto.toProduct();
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        FakeStoreResponseDto[] fakeStoreResponseDtos = restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreResponseDto[].class);
        if(fakeStoreResponseDtos == null) {
            throw new ProductNotFoundException("No Product found");
        }
        List<Product> allProducts = new ArrayList<>();
        for (FakeStoreResponseDto fakeStoreResponseDto : fakeStoreResponseDtos) {
            Product product = fakeStoreResponseDto.toProduct();
            allProducts.add(product);
        }
        return allProducts;
    }

    @Override
    public Product createProduct(
            String name,
            String Description,
            Double price,
            String imageUrl,
            String category
    ) throws ProductNotFoundException {
        Product product = new Product();
        product.setName(name);
        product.setDescription(Description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
        Category categoryObj = new Category();
        categoryObj.setName(category);
        product.setCategory(categoryObj);
    }
}
