package com.supplychain.productservicesupplychain.services;

import com.supplychain.productservicesupplychain.dtos.FakeStoreRequestDto;
import com.supplychain.productservicesupplychain.dtos.FakeStoreResponseDto;
import com.supplychain.productservicesupplychain.exceptions.ProductNotFoundException;
import com.supplychain.productservicesupplychain.models.Category;
import com.supplychain.productservicesupplychain.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    private final ProductService productService;
    RestTemplate restTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate, ProductService productService) {
        this.restTemplate = restTemplate;
        this.productService = productService;
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
            String description,
            Double price,
            String imageUrl,
            String category
    ) throws ProductNotFoundException {
        FakeStoreRequestDto remoteRequestDto = createDtoFromParams(name, description, price, imageUrl, category);
        FakeStoreResponseDto remoteResponseDto = restTemplate.postForObject("https://fakestoreapi.com/products", remoteRequestDto, FakeStoreResponseDto.class);
        return remoteResponseDto.toProduct();
    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category) throws ProductNotFoundException {

        FakeStoreResponseDto remoteResponseDto = restTemplate.getForObject("https://fakestoreapi.com/products/{id}", FakeStoreResponseDto.class, id);
        if(remoteResponseDto == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        FakeStoreRequestDto updatedFakeStoreRequestDto = createDtoFromParams(name, description, price, imageUrl, category);

//        FakeStoreResponseDto remoteResponseDtoAfterReplace = restTemplate.put(
//                "https://fakestoreapi.com/products/{id}",
//                remoteRequestDto,
//                id
//        );
        //exchange()
        //when we use exchange() it deels in ResponseEntities and http entity rathar in dtos takes httpEntity as request body and response entity as response body
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        return null;
    }

    private FakeStoreRequestDto createDtoFromParams(String name, String description, Double price, String imageUrl, String category) {
        FakeStoreRequestDto fakeStoreRequestDto = new FakeStoreRequestDto();
        fakeStoreRequestDto.setTitle(name);
        fakeStoreRequestDto.setDescription(description);
        fakeStoreRequestDto.setPrice(price);
        fakeStoreRequestDto.setImage(imageUrl);
        fakeStoreRequestDto.setCategory(category);
        return fakeStoreRequestDto;
    }
}
