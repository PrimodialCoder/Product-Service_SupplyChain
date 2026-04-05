package com.supplychain.productservicesupplychain.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Category extends BaseModel {
    private String name;
    private String description;

}
