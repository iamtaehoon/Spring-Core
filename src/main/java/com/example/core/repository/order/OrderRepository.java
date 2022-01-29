package com.example.core.repository.order;

import java.util.List;
import java.util.Optional;

import com.example.core.domain.Order;

public interface OrderRepository {
    Order save(Long memberId, Long productId, int wishToPurchaseCnt, int amountToPurchase);

    Optional<Order> findOne(Long id);

    List<Order> findOrdersUsingMemberId(Long memberId);

    void update(Long orderId, Long memberId, Long productId, int wishToPurchaseCnt, int amountToPurchase);

    void delete(Long orderId);

}
