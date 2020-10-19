package com.ford.javatest.modal;

import com.ford.javatest.util.DiscountUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.BiConsumer;

@Getter
public class CombinationDiscount extends Discount {

    @NonNull
    private Integer qualifyingProdMinQnty;
    @NonNull
    private String discountOnProductId;
    @NonNull
    private BigDecimal discountPricePercentage;
    @Builder
    public CombinationDiscount(String discountedProductId,
                               LocalDate startDate,
                               LocalDate endDate,
                               Integer qualifyingProdMinQnty,
                               String discountOnProductId,
                               BigDecimal discountPricePercentage) {
        super(discountedProductId, startDate, endDate);
        this.discountOnProductId = discountOnProductId;
        this.qualifyingProdMinQnty = qualifyingProdMinQnty;
        this.discountPricePercentage = discountPricePercentage;
    }
}
