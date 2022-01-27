package com.example.core.domain.discount;

import com.example.core.domain.Grade;

public interface Discount {

    int calculateDiscountPrice(int originalPrice, Grade grade);
}
