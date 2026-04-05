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
    private String image;
    private String category;

    public Product toProduct() {
        Product product = new Product();
        product.setName(this.title);
        product.setPrice(this.price);
        product.setDescription(this.description);
        product.setImageUrl(this.image);
        product.setCategory(new Category(this.category));
        return product;
    }
}
