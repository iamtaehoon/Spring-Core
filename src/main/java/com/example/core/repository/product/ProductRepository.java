package com.example.core.repository.product;

import com.example.core.domain.Product;

public interface ProductRepository {
    public boolean canPurchaseQuantity(Long productId, int wishToPurchaseCnt);

    public int getThisPrice(Long productId);

    public int reduce(Long productId, int wishToPurchaseCnt);
}
