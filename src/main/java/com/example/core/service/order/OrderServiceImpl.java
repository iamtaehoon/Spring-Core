package com.example.core.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.core.domain.Order;
import com.example.core.domain.OrderCheckForm;
import com.example.core.domain.OrderForm;
import com.example.core.domain.discount.Discount;
import com.example.core.domain.member.Member;
import com.example.core.domain.product.Product;
import com.example.core.repository.member.MemberRepository;
import com.example.core.repository.order.OrderRepository;
import com.example.core.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
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
    public Order purchase(OrderCheckForm orderCheckForm) {
        Long memberId = orderCheckForm.getMemberId();
        Long productId = orderCheckForm.getProductId();
        int wishToPurchaseCnt = orderCheckForm.getQuantity();
        int amountToPurchase = orderCheckForm.getCalculatePrice();

        productRepository.reduce(productId, wishToPurchaseCnt);
        return orderRepository.save(memberId, productId, wishToPurchaseCnt, amountToPurchase);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
