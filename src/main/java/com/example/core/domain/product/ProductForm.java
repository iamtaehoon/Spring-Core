package com.example.core.domain.product;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProductForm {
    private String name;
    private int price;
    private int quantity;

    public ProductForm(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
