package com.example.core.service.order;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.core.domain.Order;
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
    public int calculatePrice(String memberName, String productName, int wishToPurchaseCnt) {
        Long memberId = validateMemberIsPresent(memberName);
        Long productId = validateProductIsPresent(productName);

        int originalPrice = productRepository.getThisPrice(productId);
        int totalPrice = applyDiscount(memberId, originalPrice);
        return totalPrice * wishToPurchaseCnt;
    }

    private Long validateProductIsPresent(String productName) {
        Optional<Product> wrappingProduct = productRepository.findOneUsingName(productName);
        if (wrappingProduct.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품이 사용되었습니다.");
        }
        Long productId = wrappingProduct.get().getId();
        return productId;
    }

    private Long validateMemberIsPresent(String memberName) {
        Optional<Member> wrappingMember = memberRepository.findOneByUserId(memberName);
        if (wrappingMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 멤버가 사용되었습니다.");
        }
        Long memberId = wrappingMember.get().getId();
        return memberId;
    }

    private int applyDiscount(Long memberId, int originalPrice) {
        Member member = memberRepository.findOne(memberId).get();
        int discountPrice = discount.calculateDiscountPrice(originalPrice, member.getGrade());
        return originalPrice - discountPrice;
    }

    @Override
    public Order purchase(String memberName, String productName, int wishToPurchaseCnt) {
        Long memberId = validateMemberIsPresent(memberName);
        Long productId = validateProductIsPresent(productName);

        productRepository.reduce(productId, wishToPurchaseCnt);
        int amountToPurchase = calculatePrice(memberName, productName, wishToPurchaseCnt);
        Order order = orderRepository.save(memberId, productId, wishToPurchaseCnt, amountToPurchase);
        return order;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
