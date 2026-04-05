package com.supplychain.productservicesupplychain.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfigs {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
