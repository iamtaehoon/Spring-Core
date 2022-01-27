package com.example.core.domain;

public class Order {
    private Long orderId;
    private Long memberId;
    private Long productId;
    private int purchaseQuantity;

    public Order(Long memberId, Long productId, int purchaseQuantity) {

    }
}
