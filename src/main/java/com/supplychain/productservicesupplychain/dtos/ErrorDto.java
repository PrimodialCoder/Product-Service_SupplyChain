package com.supplychain.productservicesupplychain.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@Setter
public class ErrorDto {
    private String status;
    private String message;
}
