package com.ford.javatest.service;

import com.ford.javatest.modal.Product;
import com.ford.javatest.modal.ShoppingCart;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShoppingCartServiceTest {

    private ShoppingCartService shoppingCartService = new ShoppingCartService();

    @Test
    public void addProductToCart_givenAdding6SameProduct_shouldAddProductCorrectly() {
        ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>(), BigDecimal.ZERO);
        Product apple = Product
                .builder()
                .productId("Apple")
                .productUnit("singles")
                .productUnitPrice(BigDecimal.valueOf(.10))
                .build();
        shoppingCartService.addProductToCart(apple, 6, shoppingCart);
        assertEquals(6, shoppingCart.getCart().get(apple).getQuantity());
    }

    @Test
    public void addProductToCart_givenAdding6DifferentProduct_shouldAddProductCorrectly() {
        ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>(), BigDecimal.ZERO);
        Product apple = Product
                .builder()
                .productId("Apple")
                .productUnit("singles")
                .productUnitPrice(BigDecimal.valueOf(.10))
                .build();
        Product soup = Product
                .builder()
                .productId("Soup")
                .productUnit("tin")
                .productUnitPrice(BigDecimal.valueOf(.65))
                .build();
        shoppingCartService.addProductToCart(apple, 3, shoppingCart);
        shoppingCartService.addProductToCart(soup, 3, shoppingCart);
        assertEquals(2, shoppingCart.getCart().size());
        assertEquals(3, shoppingCart.getCart().get(apple).getQuantity());
        assertEquals(3, shoppingCart.getCart().get(soup).getQuantity());
    }

    @Test
    public void addProductToCart_givenAddingAlreadyAddedProduct_shouldNotDuplicate() {
        ShoppingCart shoppingCart = new ShoppingCart(new HashMap<>(), BigDecimal.ZERO);
        Product apple_1 = Product
                .builder()
                .productId("Apple")
                .productUnit("single")
                .productUnitPrice(BigDecimal.valueOf(.10))
                .build();
        Product apple_2 = Product
                .builder()
                .productId("Apple")
                .productUnit("single")
                .productUnitPrice(BigDecimal.valueOf(.10))
                .build();
        shoppingCartService.addProductToCart(apple_1, 2, shoppingCart);
        shoppingCartService.addProductToCart(apple_2, 2, shoppingCart);
        assertEquals(1, shoppingCart.getCart().size());
        assertEquals(4, shoppingCart.getCart().get(apple_1).getQuantity());
        assertEquals(4, shoppingCart.getCart().get(apple_2).getQuantity());
    }

}