package com.example.core.service.order;

import java.util.List;

import com.example.core.domain.Order;

public interface OrderService {
    // calculatePrice, purchase는 OrderService를 만들어주는게 나을듯. Product가 아님.
    public int calculatePrice(String memberName, String productName, int wishToPurchaseCnt);

    public Order purchase(String memberName, String productName, int wishToPurchaseCnt);

    List<Order> findAll();
}
