package com.example.core.domain;

import lombok.Getter;

@Getter
public class Order {
    private Long id;
    private Long memberId;
    private Long productId;
    private int purchaseQuantity;
    private int amountToPurchase;

    public Order(Long id, Long memberId, Long productId, int purchaseQuantity, int amountToPurchase) {
        this.id = id;
        this.memberId = memberId;
        this.productId = productId;
        this.purchaseQuantity = purchaseQuantity;
        this.amountToPurchase = amountToPurchase;
    }

}
