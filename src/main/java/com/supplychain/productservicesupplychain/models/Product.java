package com.supplychain.productservicesupplychain.models;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private long id;
    private String name;
    private String description;
    private Category category;
    private double price;
    private String imageUrl;
}
