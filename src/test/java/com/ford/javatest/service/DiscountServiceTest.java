package com.ford.javatest.service;

import com.ford.javatest.modal.CombinationDiscount;
import com.ford.javatest.modal.Product;
import com.ford.javatest.modal.ProductQuantityAndPrice;
import com.ford.javatest.modal.ShoppingCart;
import com.ford.javatest.repos.DiscountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DiscountServiceTest {

    @Mock
    private DiscountRepo discountRepo;

    private DiscountService discountService;

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setup() {
        discountService = new DiscountService(discountRepo);
        shoppingCart = new ShoppingCart(new HashMap<>(), BigDecimal.ZERO);
    }

    @Test
    public void applyDiscount_givenCombinationDiscount_shouldApplyDiscountToCart() {
        when(discountRepo.getDiscountByProductId("Soup")).thenReturn(Arrays.asList(
                CombinationDiscount.builder()
                        .discountedProductId("Soup")
                        .discountOnProductId("Bread")
                        .discountPricePercentage((BigDecimal.valueOf(50)))
                        .qualifyingProdMinQnty(2)
                        .startDate(LocalDate.now().minusDays(1))
                        .endDate(LocalDate.now().plusDays(6))
                        .build()
        ));
        Product bread = Product
                .builder()
                .productId("Bread")
                .productUnit("loaf")
                .productUnitPrice(BigDecimal.valueOf(.80))
                .build();
        Product soup = Product
                .builder()
                .productId("Soup")
                .productUnit("tin")
                .productUnitPrice(BigDecimal.valueOf(.65))
                .build();
        shoppingCart.getCart().put(soup, ProductQuantityAndPrice.builder().quantity(2).build());
        shoppingCart.getCart().put(bread, ProductQuantityAndPrice.builder().quantity(1).build());
        discountService.applyDiscount(shoppingCart);
        assertEquals(BigDecimal.valueOf(1.30).setScale(2), shoppingCart.getCart().get(soup).getTotalPrice());
        assertEquals(BigDecimal.valueOf(.40), shoppingCart.getCart().get(bread).getTotalPrice());
    }

    @Test
    public void applyDiscount_givenCombinationDiscount_AndCartHasNoBread_shouldNotApplyDiscountToCart() {
        when(discountRepo.getDiscountByProductId("Soup")).thenReturn(Arrays.asList(
                CombinationDiscount.builder()
                        .discountedProductId("Soup")
                        .discountOnProductId("Bread")
                        .discountPricePercentage((BigDecimal.valueOf(50)))
                        .qualifyingProdMinQnty(2)
                        .startDate(LocalDate.now().minusDays(1))
                        .endDate(LocalDate.now().plusDays(6))
                        .build()
        ));
        Product soup = Product
                .builder()
                .productId("Soup")
                .productUnit("tin")
                .productUnitPrice(BigDecimal.valueOf(.65))
                .build();
        shoppingCart.getCart().put(soup, ProductQuantityAndPrice.builder().quantity(2).build());
        discountService.applyDiscount(shoppingCart);
        assertEquals(BigDecimal.valueOf(1.30).setScale(2), shoppingCart.getCart().get(soup).getTotalPrice());
    }

    @Test
    public void applyDiscount_givenCombinationDiscount_AndCartHasTwoBread_shouldApplyDiscountToCartForOneBread() {
        when(discountRepo.getDiscountByProductId("Soup")).thenReturn(Arrays.asList(
                CombinationDiscount.builder()
                        .discountedProductId("Soup")
                        .discountOnProductId("Bread")
                        .discountPricePercentage((BigDecimal.valueOf(50)))
                        .qualifyingProdMinQnty(2)
                        .startDate(LocalDate.now().minusDays(1))
                        .endDate(LocalDate.now().plusDays(6))
                        .build()
        ));
        Product soup = Product
                .builder()
                .productId("Soup")
                .productUnit("tin")
                .productUnitPrice(BigDecimal.valueOf(.65))
                .build();
        Product bread = Product
                .builder()
                .productId("Bread")
                .productUnit("loaf")
                .productUnitPrice(BigDecimal.valueOf(.80))
                .build();
        shoppingCart.getCart().put(soup, ProductQuantityAndPrice.builder().quantity(2).build());
        shoppingCart.getCart().put(bread, ProductQuantityAndPrice.builder().quantity(2).build());

        discountService.applyDiscount(shoppingCart);
        assertEquals(BigDecimal.valueOf(1.30).setScale(2), shoppingCart.getCart().get(soup).getTotalPrice());
        assertEquals(BigDecimal.valueOf(1.20), shoppingCart.getCart().get(bread).getTotalPrice());
    }

}