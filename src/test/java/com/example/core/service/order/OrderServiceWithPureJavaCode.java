package com.example.core.service.order;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.core.domain.Grade;
import com.example.core.domain.Order;
import com.example.core.domain.discount.Discount;
import com.example.core.domain.discount.FixDiscount;
import com.example.core.domain.member.Member;
import com.example.core.domain.product.Product;
import com.example.core.domain.product.ProductForm;
import com.example.core.repository.member.MemoryMemberRepository;
import com.example.core.repository.order.MemoryOrderRepository;
import com.example.core.repository.product.MemoryProductRepository;

class OrderServiceWithPureJavaCode { //이름 이렇게 지으면 안되는데, 방법을 모르겠어서 일단 이렇게. mock 객체 공부해보기.
    // MemoryProductRepository productRepository = new MemoryProductRepository();
    // MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    // MemoryOrderRepository orderRepository = new MemoryOrderRepository();
    // Discount discount = new FixDiscount();
    // OrderService orderService = new OrderServiceImpl(orderRepository, memberRepository, productRepository,
    //     discount);
    //
    // @BeforeEach
    // void beforeEach() {
    //     Member member1 = memberRepository.save("user1", "pw1", Grade.VIP, "010-1111-1111");
    //     Member member2 = memberRepository.save("user2", "pw1", Grade.NORMAL, "010-1111-1111");
    //     Member member3 = memberRepository.save("user3", "pw1", Grade.NORMAL, "010-1111-1111");
    //
    //     Product product1 = productRepository.save("product1", 10000, 10);
    //     Product product2 = productRepository.save("product2", 3000, 5);
    //
    //     //구매가 실제로 일어난 게 아님. 디비에 단순하게 값을 넣어준거라 위에 product 수량 바뀌지 않음.
    //     orderRepository.save(member1.getId(), product1.getId(), 5,
    //         orderService.calculatePrice(member1.getId(), product1.getId(), 5));
    //     orderRepository.save(member2.getId(), product1.getId(), 5,
    //         orderService.calculatePrice(member2.getId(), product1.getId(), 5));
    //
    // }
    //
    // @AfterEach
    // void afterEach() {
    //     productRepository.clear();
    //     memberRepository.clear();
    //     orderRepository.clear();
    // }
    //
    // @Test
    // void VIP_할인() {
    //     Long vipId = memberRepository.findOneByUserId("user1").get().getId();
    //     Long productId = productRepository.findOneUsingName("product1").get().getId();
    //     assertThat(orderService.calculatePrice(vipId, productId, 2)).isEqualTo((int)(10000*2*0.9));
    // }
    //
    // @Test
    // void NORMAL_할인() {
    //     Long vipId = memberRepository.findOneByUserId("user2").get().getId();
    //     Long productId = productRepository.findOneUsingName("product1").get().getId();
    //     assertThat(orderService.calculatePrice(vipId, productId, 2)).isEqualTo(20000);
    // }
    //
    // @Test
    // void 상품_구매() {
    //     Long productId = productRepository.findOneUsingName("product2").get().getId();
    //     Product findProduct = productRepository.findOne(productId).get();
    //     Long memberId = memberRepository.findOneByUserId("user2").get().getId();
    //     Order purchase = orderService.purchase(memberId, productId, 5);
    //     assertThat(findProduct.getQuantity()).isEqualTo(0);
    //     assertThat(orderRepository.findOrdersUsingMemberId(memberId).size()).isEqualTo(2);
    // }
    //
}