package com.ford.javatest.repos;


import com.ford.javatest.modal.Discount;

import java.util.List;

/**
 *  Discount Repository , which fetches the available discount for the given product name.
 */
public interface DiscountRepo {

    public List<Discount> getDiscountByProductId(String productId);
}
