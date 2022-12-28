package com.api.AddressApi.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapConfig {

    @Bean
    ModelMapper mapper() {
        return new ModelMapper();
    }
}

