package com.ford.javatest.repos;

import com.ford.javatest.modal.CombinationDiscount;
import com.ford.javatest.modal.Discount;
import com.ford.javatest.modal.PercentageDiscount;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = InMemoryDiscountRepo.class)
class InMemoryDiscountRepoTest {

    @Autowired
    DiscountRepo discountRepo;

    @Test
    public void getDiscountByProductName_givenValidProductNameApple_ShouldReturnAppleDiscount() {
        LocalDate endDate = LocalDate.now();
        endDate = endDate.plusMonths(1);
        endDate = endDate.withDayOfMonth(
                endDate.getMonth().length(endDate.isLeapYear()));
        Discount expectedDiscount = PercentageDiscount
                .builder()
                .discountedProductId("Apple")
                .startDate(LocalDate.now().plusDays(3))
                .endDate(endDate)
                .discountPercentage(BigDecimal.valueOf(10))
                .build();
        List<Discount> actualDiscount = discountRepo.getDiscountByProductId("Apple");
        assertEquals(expectedDiscount.getDiscountedProductId(), actualDiscount.get(0).getDiscountedProductId());
        assertEquals(expectedDiscount.getStartDate(), actualDiscount.get(0).getStartDate());
        assertEquals(expectedDiscount.getEndDate(), actualDiscount.get(0).getEndDate());
        assertEquals(((PercentageDiscount) expectedDiscount).getDiscountPercentage(),
                ((PercentageDiscount) actualDiscount.get(0)).getDiscountPercentage());

    }

    @Test
    public void getDiscountByProductName_givenValidProductNameSoup_ShouldReturnCombinationDiscount() {
        Discount expectedDiscount = CombinationDiscount
                .builder()
                .discountedProductId("Soup")
                .discountOnProductId("Bread")
                .discountPricePercentage((BigDecimal.valueOf(50)))
                .qualifyingProdMinQnty(2)
                .startDate(LocalDate.now().minusDays(1))
                .endDate(LocalDate.now().plusDays(6))
                .build();
        List<Discount> actualDiscount = discountRepo.getDiscountByProductId("Soup");
        assertEquals(expectedDiscount.getDiscountedProductId(), actualDiscount.get(0).getDiscountedProductId());
        assertEquals(expectedDiscount.getStartDate(), actualDiscount.get(0).getStartDate());
        assertEquals(expectedDiscount.getEndDate(), actualDiscount.get(0).getEndDate());
        assertEquals(((CombinationDiscount) expectedDiscount).getDiscountPricePercentage(),
                ((CombinationDiscount) actualDiscount.get(0)).getDiscountPricePercentage());
        assertEquals(((CombinationDiscount) expectedDiscount).getQualifyingProdMinQnty(),
                ((CombinationDiscount) actualDiscount.get(0)).getQualifyingProdMinQnty());
        assertEquals(((CombinationDiscount) expectedDiscount).getDiscountOnProductId(),
                ((CombinationDiscount) actualDiscount.get(0)).getDiscountOnProductId());

    }

    @Test
    public void getDiscountByProductName_WhenProductNameNotDiscountedBread_ShouldReturnEmptyDiscount() {
        assertThat(discountRepo.getDiscountByProductId("Bread")).isEmpty();
    }

    @Test
    public void getDiscountByProductName_WhenProductNameNull_ShouldReturnEmptyDiscount() {
        assertThat(discountRepo.getDiscountByProductId(null)).isEmpty();
    }
}