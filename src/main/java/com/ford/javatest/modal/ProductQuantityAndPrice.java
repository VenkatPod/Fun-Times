package com.ford.javatest.modal;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder(toBuilder = true)
public class ProductQuantityAndPrice {
    private int quantity;
    private BigDecimal totalPrice;
}
