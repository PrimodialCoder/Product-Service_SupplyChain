package com.supplychain.productservicesupplychain.dtos;

import com.supplychain.productservicesupplychain.models.Category;
import com.supplychain.productservicesupplychain.models.Product;
import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
@Getter
@Setter
public class FakeStoreResponseDto {
    private long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;

    public Product toProduct() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.title);
        product.setPrice(this.price);
        product.setDescription(this.description);
        product.setImageUrl(this.image);
        Category category = new Category();
        category.setName(this.category);
        product.setCategory(category);
        return product;
    }
}
