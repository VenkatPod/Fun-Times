package com.ford.javatest.service;

import com.ford.javatest.modal.Product;
import com.ford.javatest.modal.ProductQuantityAndPrice;
import com.ford.javatest.modal.ShoppingCart;
import org.springframework.stereotype.Service;

/**
 * This service is reponsible for adding products to the shopping cart
 */
@Service
public class ShoppingCartService {

    public void addProductToCart(Product product, int quantityOfProducts, ShoppingCart shoppingCart) {
        shoppingCart.getCart().compute(product,
                (k, v) -> v == null ?
                        ProductQuantityAndPrice.builder().quantity(quantityOfProducts).build() :
                        v.toBuilder().quantity(v.getQuantity() + quantityOfProducts).build());
    }


}
