package com.ford.javatest.service;

import com.ford.javatest.modal.Product;
import com.ford.javatest.modal.ProductQuantityAndPrice;
import com.ford.javatest.modal.ShoppingCart;
import com.ford.javatest.repos.InMemoryDiscountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ShoppingCartService.class, DiscountService.class, InMemoryDiscountRepo.class})
public class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setup() {
        shoppingCart = new ShoppingCart(new HashMap<>(), BigDecimal.ZERO, LocalDate.now());
    }

    @Test
    public void addProductToCart_givenAdding6SameProduct_shouldAddProductCorrectly() {
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

    @Test
    public void checkOut_givenCartWith3TinsAnd2BreadAndCheckoutDatetoday_shouldDoCheckoutCorrectly() {
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
        shoppingCart.getCart().put(soup, ProductQuantityAndPrice.builder().quantity(3).build());
        shoppingCart.getCart().put(bread, ProductQuantityAndPrice.builder().quantity(2).build());
        shoppingCart.setCheckoutDate(LocalDate.now());

        shoppingCartService.checkout(shoppingCart);

        assertEquals(BigDecimal.valueOf(3.15), shoppingCart.getTotalPriceAfterDiscount());
    }

    @Test
    public void checkOut_givenCartWith6ApplesAnd1MilkAndCheckoutDateToday_shouldDoCheckoutCorrectly() {
        Product apple = Product
                .builder()
                .productId("Apple")
                .productUnit("single")
                .productUnitPrice(BigDecimal.valueOf(.10))
                .build();
        Product milk = Product
                .builder()
                .productId("Milk")
                .productUnit("bottle")
                .productUnitPrice(BigDecimal.valueOf(1.30))
                .build();
        shoppingCart.getCart().put(apple, ProductQuantityAndPrice.builder().quantity(6).build());
        shoppingCart.getCart().put(milk, ProductQuantityAndPrice.builder().quantity(1).build());
        shoppingCart.setCheckoutDate(LocalDate.now());

        shoppingCartService.checkout(shoppingCart);

        assertEquals(BigDecimal.valueOf(1.90), shoppingCart.getTotalPriceAfterDiscount());
    }

    @Test
    public void checkOut_givenCartWith6ApplesAnd1MilkAndCheckoutDate5DaysFromnow_shouldDoCheckoutCorrectly() {
        Product apple = Product
                .builder()
                .productId("Apple")
                .productUnit("single")
                .productUnitPrice(BigDecimal.valueOf(.10))
                .build();
        Product milk = Product
                .builder()
                .productId("Milk")
                .productUnit("bottle")
                .productUnitPrice(BigDecimal.valueOf(1.30))
                .build();
        shoppingCart.getCart().put(apple, ProductQuantityAndPrice.builder().quantity(6).build());
        shoppingCart.getCart().put(milk, ProductQuantityAndPrice.builder().quantity(1).build());
        shoppingCart.setCheckoutDate(LocalDate.now().plusDays(5));

        shoppingCartService.checkout(shoppingCart);

        assertEquals(BigDecimal.valueOf(1.84), shoppingCart.getTotalPriceAfterDiscount());
    }

    @Test
    public void checkOut_givenCartWith3Apples2TinsAnd1BreadAndCheckoutDate5DaysFromNow_shouldDoCheckoutCorrectly() {
        Product apple = Product
                .builder()
                .productId("Apple")
                .productUnit("single")
                .productUnitPrice(BigDecimal.valueOf(.10))
                .build();
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
        shoppingCart.getCart().put(apple, ProductQuantityAndPrice.builder().quantity(3).build());
        shoppingCart.getCart().put(soup, ProductQuantityAndPrice.builder().quantity(2).build());
        shoppingCart.getCart().put(bread, ProductQuantityAndPrice.builder().quantity(1).build());
        shoppingCart.setCheckoutDate(LocalDate.now().plusDays(5));

        shoppingCartService.checkout(shoppingCart);

        assertEquals(BigDecimal.valueOf(1.97), shoppingCart.getTotalPriceAfterDiscount());
    }

}