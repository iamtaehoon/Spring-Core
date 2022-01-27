package com.example.core.domain.discount;

import com.example.core.domain.Grade;

public interface Discount {
    //AppConfig를 여기 패키지 위치한 단계에서 적용해도 되나?
    int calculateDiscountPrice(int originalPrice, Grade grade);
}
