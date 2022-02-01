package com.example.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderForm {
    private String memberName;
    private String productName;
    private int quantity;

    public OrderForm(String memberName, String productName, int quantity) {
        this.memberName = memberName;
        this.productName = productName;
        this.quantity = quantity;
    }

}
