package com.ford.javatest.modal;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class ShoppingCart {
    private Map<Product, ProductQuantityAndPrice> cart;
    private BigDecimal totalPriceAfterDiscount;
}
