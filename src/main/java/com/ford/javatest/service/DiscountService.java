package com.ford.javatest.service;

import com.ford.javatest.modal.Discount;
import com.ford.javatest.modal.ShoppingCart;
import com.ford.javatest.repos.DiscountRepo;
import com.ford.javatest.util.DiscountUtils;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
public class DiscountService {

    private DiscountRepo discountRepo;

    public void applyDiscount(ShoppingCart shoppingCart) {
        shoppingCart.getCart()
                .forEach(
                        (product, productQuantityAndPrice) -> {
                            List<Discount> discountList =
                                    discountRepo
                                            .getDiscountByProductId(product.getProductId());
                            discountList.stream()
                                    .filter(discount ->
                                            isDiscountValid(shoppingCart.getCheckoutDate(),
                                                    discount.getStartDate(),
                                                    discount.getEndDate()))
                                    .forEach(
                                            discount ->
                                                    DiscountUtils.discountMappers.get(discount.getClass())
                                                            .accept(shoppingCart, discount));
                        });
    }

    private boolean isDiscountValid(LocalDate checkoutDate,
                                    LocalDate discountStartDate,
                                    LocalDate discountEndDate) {
        return (checkoutDate.isEqual(discountStartDate) || checkoutDate.isAfter(discountStartDate)) &&
                (checkoutDate.isEqual(discountEndDate) || checkoutDate.isBefore(discountEndDate));
    }
}
