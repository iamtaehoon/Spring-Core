package com.example.core.service.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.core.domain.Grade;
import com.example.core.domain.member.Member;
import com.example.core.domain.product.Product;
import com.example.core.domain.product.ProductForm;
import com.example.core.repository.product.MemoryProductRepository;
import com.example.core.repository.product.ProductRepository;

class ProductServiceImplTest {
    private MemoryProductRepository productRepository = new MemoryProductRepository();
    private ProductService productService = new ProductServiceImpl(productRepository);

    @BeforeEach
    void beforeEach() {
        Product product1 = productRepository.save("product1", 10000, 10);
        Product product2 = productRepository.save("product2", 3000, 5);
    }

    @AfterEach
    void afterEach() {
        productRepository.clear();
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

}