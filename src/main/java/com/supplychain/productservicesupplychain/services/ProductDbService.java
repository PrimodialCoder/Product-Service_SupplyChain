package com.supplychain.productservicesupplychain.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.supplychain.productservicesupplychain.exceptions.ProductNotFoundException;
import com.supplychain.productservicesupplychain.models.Category;
import com.supplychain.productservicesupplychain.models.Product;
import com.supplychain.productservicesupplychain.repositories.CategoryRepository;
import com.supplychain.productservicesupplychain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("productDbService")
@Primary
public class ProductDbService implements ProductService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductDbService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product getProductById(long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    @Override
    public List<Product> getAllProducts() throws ProductNotFoundException {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String name, String Description, Double price, String imageUrl, String category) throws ProductNotFoundException {
        Product product = new Product();
        buildProduct(product, name, Description, price, imageUrl, category);
        return productRepository.save(product);
    }

    private void buildProduct(Product product, String name, String description, Double price, String imageUrl, String category) {
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);

        Category categoryObj = getCategoryFromDb(category);;
        product.setCategory(categoryObj);
    }

    private Category getCategoryFromDb(String category) {
        Optional<Category> categoryOptional = categoryRepository.findByName(category);
        if(categoryOptional.isPresent()) {
            return categoryOptional.get();
        } else {
            Category categoryObj = new Category();
            categoryObj.setName(category);
            return categoryRepository.save(categoryObj);
        }
    }

    @Override
    public Product replaceProduct(long id, String name, String description, double price, String imageUrl, String category) throws ProductNotFoundException {
        Product product = new Product();
        product.setId(id);
        buildProduct(product, name, description, price, imageUrl, category);

        return productRepository.save(product);
    }

    @Override
    public Product applyPatchToProduct(long id, JsonPatch patch) throws ProductNotFoundException, JsonPatchException, JsonProcessingException {
        return null;
    }
}
