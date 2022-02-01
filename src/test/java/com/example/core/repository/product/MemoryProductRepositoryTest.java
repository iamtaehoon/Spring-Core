package com.example.core.repository.product;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.core.domain.product.Product;

class MemoryProductRepositoryTest {
    MemoryProductRepository productRepository = new MemoryProductRepository();

    @BeforeEach
    void beforeEach() {
        productRepository.save("상품1", 1400, 2);
        productRepository.save("상품2", 1000, 10);
        productRepository.save("상품3", 40500, 0);
        productRepository.save("상품4", 32000, 3);
    }
    @AfterEach
    void afterEach() {
        productRepository.clear();
    }

    @Test
    void 상품_이름으로_조회() {
        Product product = productRepository.findOneUsingName("상품1").get();
        assertThat(product.getPrice()).isEqualTo(1400);
        assertThat(product.getQuantity()).isEqualTo(2);
    }

    @Test
    void 상품_이름으로_조회_없는이름() {
        assertThat(productRepository.findOneUsingName("상품11ㅇ1")).isEqualTo(Optional.empty());
    }

    @Test
    void 상품_조회() {
        Product original = productRepository.save("상품5", 10000, 52);
        Product findProduct = productRepository.findOne(original.getId()).get();

        assertThat(findProduct.getName()).isEqualTo(original.getName());
        assertThat(findProduct.getPrice()).isEqualTo(original.getPrice());
        assertThat(findProduct.getQuantity()).isEqualTo(original.getQuantity());
    }

    @Test
    void 상품_없는id_조회() {
        assertThat(productRepository.findOne(124971L)).isEqualTo(Optional.empty());
    }

    @Test
    void 전체_상품_조회() {
        List<Product> all = productRepository.findAll();
        assertThat(all.size()).isEqualTo(4);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,15,231,3,2,4,0})
    void 구매_가능_체크_테스트(int purchaseCnt) {
        List<Product> all = productRepository.findAll();
        for (Product product : all) {
            boolean canBuy = productRepository.canPurchaseQuantity(product.getId(), purchaseCnt);
            if (product.getQuantity() >= purchaseCnt) {
                assertThat(canBuy).isTrue();
            }
            else if(product.getQuantity() < purchaseCnt) {
                assertThat(canBuy).isFalse();
            }
        }
    }

    @Test
    void 상품_정보_수정() {
        Product original = productRepository.findOneUsingName("상품3").get();
        Long productId = original.getId();
        productRepository.modify(productId, "상품3", 20000, 100);
        Product findProduct = productRepository.findOneUsingName("상품3").get();

        assertThat(findProduct.getPrice()).isEqualTo(20000);
        assertThat(findProduct.getQuantity()).isEqualTo(100);
    }

    // public int reduce(Long productId, int wishToPurchaseCnt) {

    @ParameterizedTest
    @ValueSource(ints = {1, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void 상품_구매(int wishToPurchaseCnt) {
        Product original = productRepository.findOneUsingName("상품2").get();
        int quantity = original.getQuantity();
        Long productId = original.getId();

        productRepository.reduce(productId, wishToPurchaseCnt);
        Product product = productRepository.findOne(productId).get();

        assertThat(product.getQuantity()).isEqualTo(quantity - wishToPurchaseCnt);
    }

    @ParameterizedTest
    @ValueSource(ints = {11, 15, 20})
    void 상품_구매_불가(int wishToPurchaseCnt) {
        Product original = productRepository.findOneUsingName("상품2").get();
        Long productId = original.getId();

        assertThatThrownBy(() -> productRepository.reduce(productId, wishToPurchaseCnt))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("남아있는 개수보다 더 많은 상품을 구매할 수 없습니다.");
    }
}