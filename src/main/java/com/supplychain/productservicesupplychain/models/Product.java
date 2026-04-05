package com.supplychain.productservicesupplychain.models;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel {
    private String name;
    private String description;
    private Category category;
    private double price;
    private String imageUrl;

}
