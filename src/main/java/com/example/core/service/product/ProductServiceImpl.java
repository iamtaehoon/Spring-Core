package com.example.core.service.product;

import org.springframework.stereotype.Service;

import com.example.core.domain.Order;
import com.example.core.domain.discount.Discount;
import com.example.core.domain.member.Member;
import com.example.core.domain.product.Product;
import com.example.core.domain.product.ProductDTO;
import com.example.core.domain.product.ProductForm;
import com.example.core.repository.order.OrderRepository;
import com.example.core.repository.member.MemberRepository;
import com.example.core.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final Discount discount;

    @Override
    public ProductDTO register(ProductForm productForm) {
        validateAlreadyRegister(productForm.getName());
        // 수량 올바른가 테스트는 컨트롤러에서 해주면 됨 입력값에 문제임.
        Product saveProduct = productRepository.save(productForm.getName(), productForm.getPrice(),
            productForm.getQuantity());
        return new ProductDTO(saveProduct);
    }

    private void validateAlreadyRegister(String name) {
        if (productRepository.findOneUsingName(name).isPresent()) {
            throw new IllegalArgumentException("해당 상품은 이미 등록된 상품입니다.");
        }
    }

    @Override
    public Long dropOut(Long productId) {
        productRepository.delete(productId);
        return productId;
    }

    @Override
    public ProductDTO modifyDetails(Long productId, ProductForm productForm) {
        //먼저 id를 사용해 지워준다. 트랜잭션 걸려서, 밑에서 오류뜨면 안지워지겠지? 만약 지워지면 alreadyRegister 대신 다른 메서드 만들어야함.
        productRepository.delete(productId);
        //이름을 사용해 이미 등록된건지 확인한다.
        validateAlreadyRegister(productForm.getName());
        Product saveProduct = productRepository.modify(productId, productForm.getName(), productForm.getPrice(),
            productForm.getQuantity());
        return new ProductDTO(saveProduct);
    }

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
        Order order = orderRepository.save(memberId, productId, wishToPurchaseCnt);
        return order;
    }
}
