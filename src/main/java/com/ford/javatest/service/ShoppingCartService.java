package com.ford.javatest.service;

import com.ford.javatest.modal.Product;
import com.ford.javatest.modal.ProductQuantityAndPrice;
import com.ford.javatest.modal.ShoppingCart;
import com.ford.javatest.util.DiscountUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * This service is reponsible for adding products to the shopping cart
 */
@Service
@AllArgsConstructor
public class ShoppingCartService {

    private DiscountService discountService;

    public void addProductToCart(Product product, int quantityOfProducts, ShoppingCart shoppingCart) {
        shoppingCart.getCart().compute(product,
                (k, v) -> v == null ?
                        ProductQuantityAndPrice.builder().quantity(quantityOfProducts).build() :
                        v.toBuilder().quantity(v.getQuantity() + quantityOfProducts).build());
    }

    public void checkout(ShoppingCart cart) {
        discountService.applyDiscount(cart);
        cart.getCart().entrySet().stream()
                .filter(productProductQuantityAndPriceEntry -> Objects.isNull(productProductQuantityAndPriceEntry.getValue().getTotalPrice()))
                .forEach(productProductQuantityAndPriceEntry ->
                        productProductQuantityAndPriceEntry.getValue()
                                .setTotalPrice(DiscountUtils.getTotalPrice(
                                        productProductQuantityAndPriceEntry.getValue().getQuantity(),
                                        productProductQuantityAndPriceEntry.getKey().getProductUnitPrice()
                                ))
                );
        cart.setTotalPriceAfterDiscount(
                cart.getCart().values().stream()
                        .map(ProductQuantityAndPrice::getTotalPrice)
                        .reduce(BigDecimal.ZERO, (productTotal, total) -> total.add(productTotal))
        );
    }


}
