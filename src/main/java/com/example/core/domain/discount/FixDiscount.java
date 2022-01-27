package com.example.core.domain.discount;

import com.example.core.domain.Grade;

public class FixDiscount implements Discount {
    public static Double DISCOUNT_RATE = 0.1;

    @Override
    public int calculateDiscountPrice(int originalPrice, Grade grade) {
        return originalPrice * (int) Math.floor(DISCOUNT_RATE);
    }
}
