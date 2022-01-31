package com.example.core.service.product;

import java.util.List;

import com.example.core.domain.Order;
import com.example.core.domain.member.MemberDTO;
import com.example.core.domain.product.ProductDTO;
import com.example.core.domain.product.ProductForm;

public interface ProductService {
    public ProductDTO register(ProductForm productForm);

    public Long dropOut(Long productId);

    public ProductDTO modifyDetails(Long productId, ProductForm productForm);

    public List<ProductDTO> findAll();

    public ProductDTO lookUp(Long id);
}
