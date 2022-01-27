package com.example.core.domain.discount;

import com.example.core.domain.Grade;

public class FixDiscount implements Discount {
    public static Double DISCOUNT_RATE = 0.1;

    @Override
    public int calculateDiscountPrice(int originalPrice, Grade grade) {
        if (grade == Grade.VIP) {
            return (int) Math.floor(originalPrice * (DISCOUNT_RATE));
        }
        if (grade == Grade.NORMAL) {
            return 0;
        }
        throw new IllegalStateException("불가능한 상태");
    }
}
