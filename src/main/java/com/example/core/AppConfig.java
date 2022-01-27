package com.example.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.core.domain.discount.Discount;
import com.example.core.domain.discount.FixDiscount;

@Configuration
public class AppConfig {

    @Bean
    public Discount discount() {
        return new FixDiscount();
    }
}
