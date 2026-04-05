package com.supplychain.productservicesupplychain.services;

import com.supplychain.productservicesupplychain.dtos.FakeStoreResponseDto;
import com.supplychain.productservicesupplychain.models.Category;
import com.supplychain.productservicesupplychain.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FakeStoreProductService implements ProductService {

    RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(long id) {
        FakeStoreResponseDto fakeStoreResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreResponseDto.class, id);
        return fakeStoreResponseDto.toProduct();
    }

}
