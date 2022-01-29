package com.example.core.service.product;

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

class ProductServiceWithPureJavaCode { //이름 이렇게 지으면 안되는데, 방법을 모르겠어서 일단 이렇게. mock 객체 공부해보기.
    MemoryProductRepository productRepository = new MemoryProductRepository();
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemoryOrderRepository orderRepository = new MemoryOrderRepository();
    Discount discount = new FixDiscount();
    ProductService productService = new ProductServiceImpl(productRepository, memberRepository, orderRepository,
        discount);

    @BeforeEach
    void beforeEach() {
        Member member1 = memberRepository.save("user1", "pw1", Grade.VIP, "010-1111-1111");
        Member member2 = memberRepository.save("user2", "pw1", Grade.NORMAL, "010-1111-1111");
        Member member3 = memberRepository.save("user3", "pw1", Grade.NORMAL, "010-1111-1111");

        Product product1 = productRepository.save("product1", 10000, 10);
        Product product2 = productRepository.save("product2", 3000, 5);

        //구매가 실제로 일어난 게 아님. 디비에 단순하게 값을 넣어준거라 위에 product 수량 바뀌지 않음.
        orderRepository.save(member1.getId(), product1.getId(), 5,
            productService.calculatePrice(member1.getId(), product1.getId(), 5));
        orderRepository.save(member2.getId(), product1.getId(), 5,
            productService.calculatePrice(member2.getId(), product1.getId(), 5));

    }

    @AfterEach
    void afterEach() {
        productRepository.clear();
        memberRepository.clear();
        orderRepository.clear();
    }

    @Test
    void 상품_등록() {
        Long id = productService.register(new ProductForm("product3", 30000, 100)).getId();
        Product findProduct = productRepository.findOne(id).get();
        assertThat(findProduct.getName()).isEqualTo("product3");
        assertThat(findProduct.getPrice()).isEqualTo(30000);
        assertThat(findProduct.getQuantity()).isEqualTo(100);
    }

    @Test
    void 상품_등록_이미_존재하는_상품_예외() {
        assertThatThrownBy(() -> productService.register(new ProductForm("product2", 30000, 100)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("해당 상품은 이미 등록된 상품입니다.");
    }

    @Test
    void 상품_수정() {
        Long id = productRepository.findOneUsingName("product2").get().getId();
        productService.modifyDetails(id, new ProductForm("product10", 2000, 300));

        Product findProduct = productRepository.findOne(id).get();

        assertThat(findProduct.getName()).isEqualTo("product10");
        assertThat(findProduct.getPrice()).isEqualTo(2000);
        assertThat(findProduct.getQuantity()).isEqualTo(300);
    }

    @Test
    void 상품_구매() {
        Long productId = productRepository.findOneUsingName("product2").get().getId();
        Product findProduct = productRepository.findOne(productId).get();
        Long memberId = memberRepository.findOneByUserId("user2").get().getId();
        Order purchase = productService.purchase(memberId, productId, 5);
        assertThat(findProduct.getQuantity()).isEqualTo(0);
        assertThat(orderRepository.findOrdersUsingMemberId(memberId).size()).isEqualTo(2);
    }

    @Test
    void VIP_할인() {
        Long vipId = memberRepository.findOneByUserId("user1").get().getId();
        Long productId = productRepository.findOneUsingName("product1").get().getId();
        assertThat(productService.calculatePrice(vipId, productId, 2)).isEqualTo((int)(10000*2*0.9));
    }

    @Test
    void NORMAL_할인() {
        Long vipId = memberRepository.findOneByUserId("user2").get().getId();
        Long productId = productRepository.findOneUsingName("product1").get().getId();
        assertThat(productService.calculatePrice(vipId, productId, 2)).isEqualTo(20000);
    }

}