package com.example.core.service.order;

import com.example.core.domain.Order;

public interface OrderService {
    // calculatePrice, purchase는 OrderService를 만들어주는게 나을듯. Product가 아님.
    public int calculatePrice(Long memberId, Long productId, int wishToPurchaseCnt);

    public Order purchase(Long memberId, Long productId, int wishToPurchaseCnt);


}
