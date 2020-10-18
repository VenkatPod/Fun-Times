package com.ford.javatest.modal;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class PercentageDiscount extends Discount {

    @NonNull
    private BigDecimal discountPercentage;

    @Builder
    public PercentageDiscount(String discountedProductId,
                              LocalDate startDate,
                              LocalDate endDate,
                              BigDecimal discountPercentage) {
        super(discountedProductId, startDate, endDate);
        this.discountPercentage = discountPercentage;
    }
}
