package com.example.core.service.order;

import java.util.List;

import com.example.core.domain.Order;
import com.example.core.domain.OrderCheckForm;
import com.example.core.domain.OrderForm;

public interface OrderService {
    // calculatePrice, purchase는 OrderService를 만들어주는게 나을듯. Product가 아님.
    public int calculatePrice(Long memberId, Long productId, int wishToPurchaseCnt);

    List<Order> findAll();

    Order purchase(OrderCheckForm orderCheckForm);
}
