package com.example.core.service.order;

import com.example.core.domain.Order;
import com.example.core.domain.discount.Discount;
import com.example.core.domain.member.Member;
import com.example.core.repository.member.MemberRepository;
import com.example.core.repository.order.OrderRepository;
import com.example.core.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final Discount discount;

    @Override
    public int calculatePrice(Long memberId, Long productId, int wishToPurchaseCnt) {
        int originalPrice = productRepository.getThisPrice(productId);
        int totalPrice = applyDiscount(memberId, originalPrice);
        return totalPrice * wishToPurchaseCnt;
    }

    private int applyDiscount(Long memberId, int originalPrice) {
        Member member = memberRepository.findOne(memberId).get();
        int discountPrice = discount.calculateDiscountPrice(originalPrice, member.getGrade());
        return originalPrice - discountPrice;
    }

    @Override
    public Order purchase(Long memberId, Long productId, int wishToPurchaseCnt) {
        productRepository.reduce(productId, wishToPurchaseCnt);
        int amountToPurchase = calculatePrice(memberId, productId, wishToPurchaseCnt);
        Order order = orderRepository.save(memberId, productId, wishToPurchaseCnt, amountToPurchase);
        return order;
    }
}
