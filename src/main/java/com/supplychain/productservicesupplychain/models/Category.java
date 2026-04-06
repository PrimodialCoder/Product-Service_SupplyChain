package com.supplychain.productservicesupplychain.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Category {
    private long id;
    private String name;

    public Category(String category) {
        this.name = category;
    }
}
