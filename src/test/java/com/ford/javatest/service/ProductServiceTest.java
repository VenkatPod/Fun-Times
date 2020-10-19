package com.ford.javatest.service;

import com.ford.javatest.modal.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private List<Product> lstOfProduct;

    private ProductService service;

    @BeforeEach
    public void setup() {
        service = new ProductService();
        lstOfProduct = Arrays.asList(
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
                        .build());
    }

    @Test
    public void getAddProductsMenu_ShouldListAllAvialableProducts() {
        String expectedResult = "------------Add Products Menu-------------\n" +
                "1.Apple\n" +
                "2.Bread\n" +
                "3.Milk\n" +
                "x. Previous Menu\n" +
                "To add enter corresponding product number or x to go back to main menu :";
        assertEquals(expectedResult, service.getAddProductsMenu(lstOfProduct));
    }

    @Test
    public void getProductFromUserInput_givenInputIs1_ShouldReturnAppleProduct() {
        Product expectedProduct = Product
                .builder()
                .productId("Apple")
                .productUnit("single")
                .productUnitPrice(BigDecimal.valueOf(.10))
                .build();
        Product actualProduct = service.getProductFromUserInput(1, lstOfProduct);
        assertNotNull(actualProduct);
        assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void getProductFromUserInput_WhenInputIsminus1_ShouldReturnNullProduct() {
        assertNull(service.getProductFromUserInput(-1, lstOfProduct));
    }

    @Test
    public void getProductFromUserInput_WhenInputIsGreaterThanListSize_ShouldReturnNullProduct() {
        assertNull(service.getProductFromUserInput(5, lstOfProduct));
    }

}