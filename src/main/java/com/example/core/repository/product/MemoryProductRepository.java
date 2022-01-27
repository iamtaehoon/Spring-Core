package com.example.core.repository.product;

import java.util.HashMap;
import java.util.Map;

import com.example.core.domain.Product;

public class MemoryProductRepository implements ProductRepository {
    private static final Map<Long, Product> repository = new HashMap<>();

    @Override
    public boolean canPurchaseQuantity(Long productId, int wishToPurchaseCnt) {
        return false;
    }

    @Override
    public int getThisPrice(Long productId) {
        return 0;
    }

    @Override
    public int reduce(Long productId, int wishToPurchaseCnt) {
        return 0;
    }
}
