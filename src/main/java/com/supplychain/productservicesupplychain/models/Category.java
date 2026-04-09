package com.supplychain.productservicesupplychain.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Category extends BaseModel {
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
