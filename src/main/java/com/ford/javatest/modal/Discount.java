package com.ford.javatest.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public abstract class Discount {

    private String discountedProductId;
    private LocalDate startDate;
    private LocalDate endDate;
}
