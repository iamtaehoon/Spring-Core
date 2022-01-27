package com.example.core.domain.product;

import lombok.Getter;

@Getter
public class ProductDTO {
    private Long id;
    private String name;
    private int price;
    private int quantity;

    public ProductDTO(Product saveProduct) {
        this.id = saveProduct.getId();
        this.name = saveProduct.getName();
        this.price = saveProduct.getPrice();
        this.quantity = saveProduct.getQuantity();
    }
}
