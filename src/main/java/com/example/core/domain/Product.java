package com.example.core.domain;

public class Product {
    private Long id;
    private String name;
    private int price;
    private int quantity;

    public Product(Long id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void reduceQuantity(int wishToPurchaseCnt) {
        if (quantity < wishToPurchaseCnt) {
            throw new IllegalArgumentException("남아있는 개수보다 더 많은 상품을 구매할 수 없습니다.");
        }
        this.quantity = this.quantity - wishToPurchaseCnt;
    }
}
