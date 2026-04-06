package com.supplychain.productservicesupplychain.services;

import com.supplychain.productservicesupplychain.exceptions.ProductNotFoundException;
import com.supplychain.productservicesupplychain.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(long id) throws ProductNotFoundException;
    List<Product> getAllProducts() throws ProductNotFoundException;
    Product createProduct(String name, String Description, Double price, String imageUrl, String category) throws ProductNotFoundException;
}
