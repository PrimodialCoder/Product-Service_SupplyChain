package com.supplychain.productservicesupplychain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product extends BaseModel {
    private String description;
//    1 product - 1 category
//    M products - 1 category
    @ManyToOne
    private Category category;
    private double price;
    private String imageUrl;
}
