package com.ford.javatest.util;

import com.ford.javatest.modal.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class DiscountUtils {

    public static Map<Class, BiConsumer<ShoppingCart, Discount>> discountMappers =
            new HashMap<Class, BiConsumer<ShoppingCart, Discount>>() {{
                put(CombinationDiscount.class, combinationalDiscount());
                put(PercentageDiscount.class, percentageDiscount());
            }};

    public static LocalDate getEndofNextMonthDate(LocalDate fromDate) {
        LocalDate endDateNextMonth = fromDate.plusMonths(1);
        endDateNextMonth = endDateNextMonth.withDayOfMonth(
                endDateNextMonth.getMonth().length(endDateNextMonth.isLeapYear()));

        return endDateNextMonth;
    }

    private static BiConsumer<ShoppingCart, Discount> combinationalDiscount() {
        return (shoppingCart, discount) -> {
            Product discountProduct = shoppingCart.getCart().keySet().stream()
                    .filter(product -> product.getProductId().equalsIgnoreCase(discount.getDiscountedProductId()))
                    .findFirst()
                    .get();

            ProductQuantityAndPrice discountProductQuaityAndPrice = shoppingCart.getCart().get(discountProduct);
            discountProductQuaityAndPrice.setTotalPrice(
                    getTotalPrice(discountProductQuaityAndPrice.getQuantity(),
                            discountProduct.getProductUnitPrice()));

            int combination = getCombination(
                    shoppingCart.getCart().get(discountProduct).getQuantity(),
                    ((CombinationDiscount) discount).getQualifyingProdMinQnty());

            shoppingCart.getCart().keySet().stream()
                    .filter(product -> product.getProductId().equalsIgnoreCase(((CombinationDiscount) discount).getDiscountOnProductId()))
                    .findFirst()
                    .ifPresent(eligibleProduct -> {
                        ProductQuantityAndPrice eligibleProductPrice = shoppingCart.getCart().get(eligibleProduct);
                        //get the minimum discount eligible count and calculate discount price
                        int eligibleDiscountCount = Math.min(combination, eligibleProductPrice.getQuantity());

                        BigDecimal discountPrice = getDiscountPrice(eligibleProduct.getProductUnitPrice(),
                                ((CombinationDiscount) discount).getDiscountPricePercentage(),
                                eligibleDiscountCount);

                        // set the total price after discount
                        eligibleProductPrice.setTotalPrice(
                                getTotalPrice(eligibleProductPrice.getQuantity(),
                                        eligibleProduct.getProductUnitPrice()).subtract(discountPrice));
                    });
        };
    }

    private static BigDecimal getTotalPrice(int quantity, BigDecimal productUnitPrice) {
        return productUnitPrice
                .multiply(BigDecimal.valueOf(quantity));
    }

    private static BigDecimal getDiscountPrice(BigDecimal eligibleProductUnitPrice,
                                               BigDecimal discountedPricePercentage,
                                               int eligibleDiscountCount) {
        return eligibleProductUnitPrice
                .multiply(discountedPricePercentage)
                .divide(BigDecimal.valueOf(100))
                .multiply(new BigDecimal(eligibleDiscountCount));
    }

    private static int getCombination(int discountProductQuatity,
                                      int qualifyingMinQuantityForDiscount) {
        return BigDecimal.valueOf(discountProductQuatity)
                .divideToIntegralValue(BigDecimal.valueOf(qualifyingMinQuantityForDiscount)).intValue();
    }

    private static BiConsumer<ShoppingCart, Discount> percentageDiscount() {
        return (shoppingCart, discount) -> {
            System.out.println(shoppingCart.getCart());
            System.out.println(discount);
        };
    }
}
