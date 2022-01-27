package com.example.core.repository.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.core.domain.product.Product;

@Repository
public class MemoryProductRepository implements ProductRepository {
    private static final Map<Long, Product> repository = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public Product save(String name, int price, int quantity) {
        Product product = new Product(sequence++, name, price, quantity);
        repository.put(product.getId(), product);
        return product;
    }

    @Override
    public Product modify(Long id, String name, int price, int quantity) {
        Product product = new Product(id, name, price, quantity);
        repository.put(id, product);
        return product;
    }

    @Override
    public Optional<Product> findOne(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Optional<Product> findOneUsingName(String name) {
        return repository.values().stream()
            .filter(product -> product.getName().equals(name)).findAny();
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public boolean canPurchaseQuantity(Long productId, int wishToPurchaseCnt) {
        Product productWantPurchase = findOne(productId).get();
        return productWantPurchase.getQuantity() >= wishToPurchaseCnt;
    }

    @Override
    public int getThisPrice(Long productId) {
        return findOne(productId).get().getPrice();
    }

    @Override
    public Product update(Long id, String name, int price, int quantity) {
        Product product = new Product(id, name, price, quantity);
        repository.put(id, product);
        return product;
    }

    @Override
    public int reduce(Long productId, int wishToPurchaseCnt) {
        Product product = findOne(productId).get();
        product.reduceQuantity(wishToPurchaseCnt);
        return product.getQuantity();
    }

    @Override
    public void delete(Long id) {
        repository.remove(id);
    }

    public void clear() {
        repository.clear();
    }
}
