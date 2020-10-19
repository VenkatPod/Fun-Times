package com.ford.javatest.modal;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class Product {
    private String productId;
    private String productUnit;
    private BigDecimal productUnitPrice;
}
