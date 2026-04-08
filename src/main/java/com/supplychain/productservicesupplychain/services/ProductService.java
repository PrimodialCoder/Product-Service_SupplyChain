package com.supplychain.productservicesupplychain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.supplychain.productservicesupplychain.exceptions.ProductNotFoundException;
import com.supplychain.productservicesupplychain.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts() throws ProductNotFoundException;
    Product createProduct(String name, String Description, Double price, String imageUrl, String category) throws ProductNotFoundException;
    Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category) throws ProductNotFoundException;
    Product applyPatchToProduct(long id, JsonPatch patch) throws ProductNotFoundException, JsonPatchException, JsonProcessingException;
}
