package com.ford.javatest.repos;

import com.ford.javatest.modal.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = InMemoryProductRepo.class)
class InMemoryProductRepoTest {

    @Autowired
    ProductRepo repos;

    @Test
    public void getProductByName_givenValidProductName_shouldReturnValidProduct() {
        Product expectedProduct = Product
                .builder()
                .productId("Apple")
                .productUnit("single")
                .productUnitPrice(BigDecimal.valueOf(.10))
                .build();
        Product product = repos.getProductById("Apple");
        assertNotNull(product);
        assertEquals(expectedProduct, product);
    }

    @Test
    public void getProductByName_givenInValidProductName_ShouldReturnNoProduct() {
        Product product = repos.getProductById("Noproduct");
        assertNull(product);
    }

    @Test
    public void getProductByName_givenNullProduct_ShouldReturnNoProduct() {
        Product product = repos.getProductById(null);
        assertNull(product);
    }


    @Test
    public void getAllAvailableProducts_shouldReturnAllProductsInStock() {
        List<Product> expectedProducts = Arrays.asList(
                Product
                        .builder()
                        .productId("Apple")
                        .productUnit("single")
                        .productUnitPrice(BigDecimal.valueOf(.10))
                        .build(),
                Product
                        .builder()
                        .productId("Bread")
                        .productUnit("loaf")
                        .productUnitPrice(BigDecimal.valueOf(.80))
                        .build(),
                Product
                        .builder()
                        .productId("Milk")
                        .productUnit("bottle")
                        .productUnitPrice(BigDecimal.valueOf(1.30))
                        .build(),
                Product
                        .builder()
                        .productId("Soup")
                        .productUnit("tin")
                        .productUnitPrice(BigDecimal.valueOf(.65))
                        .build());
        List<Product> lstOfProducts = repos.getAllAvailableProducts();
        assertEquals(4, lstOfProducts.size());
        assertEquals(expectedProducts, lstOfProducts);
    }
}