package com.supplychain.productservicesupplychain.services;

import com.supplychain.productservicesupplychain.models.Product;

public interface ProductService {
    Product getProductById(long id);
}
