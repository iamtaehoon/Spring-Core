package com.example.core.repository.product;

import java.util.List;
import java.util.Optional;

import com.example.core.domain.Product;

public interface ProductRepository {
    public Product save(String name, int price, int quantity);

    public Optional<Product> findOne(Long id);

    public List<Product> findAll();

    public boolean canPurchaseQuantity(Long id, int wishToPurchaseCnt);

    public int getThisPrice(Long id);

    public Product update(Long id, String name, int price, int quantity);

    public int reduce(Long id, int wishToPurchaseCnt);

    public void delete(Long id);
}