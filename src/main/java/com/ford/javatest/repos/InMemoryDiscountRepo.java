package com.ford.javatest.repos;

import com.ford.javatest.modal.CombinationDiscount;
import com.ford.javatest.modal.Discount;
import com.ford.javatest.modal.PercentageDiscount;
import com.ford.javatest.util.DiscountUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Repo which does inmemory initialization of Discounts
 */
@Repository
public class InMemoryDiscountRepo implements DiscountRepo {

    private List<Discount> discounts;

    /**
     * initialize with all available discounts
     */
    @PostConstruct
    public void initializeProducts() {
        discounts = Arrays.asList(
                PercentageDiscount.builder()
                        .discountedProductId("Apple")
                        .startDate(LocalDate.now().plusDays(3))
                        .endDate(DiscountUtils.getEndofNextMonthDate(LocalDate.now()))
                        .discountPercentage(BigDecimal.valueOf(10))
                        .build(),
                CombinationDiscount.builder()
                        .discountedProductId("Soup")
                        .discountOnProductId("Bread")
                        .discountPricePercentage((BigDecimal.valueOf(50)))
                        .qualifyingProdMinQnty(2)
                        .startDate(LocalDate.now().minusDays(1))
                        .endDate(LocalDate.now().plusDays(6))
                        .build());
    }

    @Override
    public List<Discount> getDiscountByProductId(String productId) {
        return discounts.stream()
                .filter(discount -> discount.getDiscountedProductId().equalsIgnoreCase(productId))
                .collect(Collectors.toList());
    }
}
