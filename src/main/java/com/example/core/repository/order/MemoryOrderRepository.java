package com.example.core.repository.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.core.domain.Order;

@Repository
public class MemoryOrderRepository implements OrderRepository {
    private static final Map<Long, Order> repository = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Order save(Long memberId, Long productId, int wishToPurchaseCnt, int amountToPurchase) {
        Order order = new Order(sequence++, memberId, productId, wishToPurchaseCnt, amountToPurchase);
        repository.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> findOne(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<Order> findOrdersUsingMemberId(Long memberId) {
        return repository.values().stream()
            .filter(order -> order.getMemberId() == memberId)
            .collect(Collectors.toList());
    }

    @Override
    public void update(Long orderId, Long memberId, Long productId, int wishToPurchaseCnt, int amountToPurchase) {
        Order order = new Order(orderId, memberId, productId, wishToPurchaseCnt, amountToPurchase);
        repository.put(orderId, order);
    }

    @Override
    public void delete(Long orderId) {
        repository.remove(orderId);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(repository.values());
    }

    public void clear() {
        repository.clear();
    }
}
