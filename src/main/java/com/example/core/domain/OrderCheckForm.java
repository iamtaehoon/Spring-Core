package com.example.core.domain;

import lombok.Getter;

@Getter
public class OrderCheckForm {
    private Long memberId;
    private Long productId;
    private int quantity;
    private int calculatePrice;

    public OrderCheckForm(Long memberId, Long productId, int quantity, int calculatePrice) {
        this.memberId = memberId;
        this.productId = productId;
        this.quantity = quantity;
        this.calculatePrice = calculatePrice;
    }
}
