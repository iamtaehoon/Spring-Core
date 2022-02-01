package com.example.core.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<ProductDTO> findAll() {
        List<Product> allProducts = productRepository.findAll();
        List<ProductDTO> allProductsDTO = new ArrayList<>();
        allProducts.stream().forEach(product -> allProductsDTO.add(new ProductDTO(product)));
        return allProductsDTO;
    }

    @Override
    public ProductDTO lookUp(Long id) {
        Optional<Product> wrappingProduct = productRepository.findOne(id);
        if (wrappingProduct.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품 ID입니다.");
        }
        return new ProductDTO(wrappingProduct.get());
    }

    @Override
    public Long findOneUsingName(String productName) {
        Optional<Product> wrappingProduct = productRepository.findOneUsingName(productName);
        if (wrappingProduct.isEmpty()) {
            throw new IllegalArgumentException("해당 상품은 존재하지 않습니다.");
        }
        return wrappingProduct.get().getId();
    }
}
