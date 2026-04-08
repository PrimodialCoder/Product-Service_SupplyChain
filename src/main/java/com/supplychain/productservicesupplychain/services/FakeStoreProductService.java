package com.supplychain.productservicesupplychain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
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
import java.util.Locale;

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
        //when we use exchange() it deals in ResponseEntities and http entity rathar in dtos takes httpEntity as request body and response entity as response body
        //for http exchange it is a generic method for which we have to create http entity and it sends the response in the form of response entity
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FakeStoreRequestDto> requestEntity = new HttpEntity<>(updatedFakeStoreRequestDto, headers);
        ResponseEntity<FakeStoreResponseDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT,
                requestEntity,
                FakeStoreResponseDto.class
        );
        return responseEntity.getBody().toProduct();
    }

    @Override
    public Product applyPatchToProduct(long id, JsonPatch patch) throws ProductNotFoundException, JsonPatchException, JsonProcessingException {
        Product existingProduct = getProductById(id);
        if(existingProduct == null) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode productNode = objectMapper.valueToTree(existingProduct);
        JsonNode patchedProductNode = patch.apply(productNode);
        Product patchedProduct = objectMapper.treeToValue(patchedProductNode, Product.class);
        return replaceProduct(
                id,
                patchedProduct.getName(),
                patchedProduct.getDescription(),
                patchedProduct.getPrice(),
                patchedProduct.getCategory().getName(),
                patchedProduct.getImageUrl()
        );
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
