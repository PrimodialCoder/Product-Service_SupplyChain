package com.supplychain.productservicesupplychain.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreRequestDto {
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
}
