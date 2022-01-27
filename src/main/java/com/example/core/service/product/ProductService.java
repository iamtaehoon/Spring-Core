package com.example.core.service.product;

import com.example.core.domain.Order;
import com.example.core.domain.product.ProductDTO;
import com.example.core.domain.product.ProductForm;

public interface ProductService {
    public ProductDTO register(ProductForm productForm);

    public ProductDTO dropOut(Long productId);

    public ProductDTO modifyDetails(Long productId, ProductForm productForm);

    public int calculatePrice(Long productId, int wishToPurchaseCnt);

    public Order purchase(Long productId, int wishToPurchaseCnt);

}
