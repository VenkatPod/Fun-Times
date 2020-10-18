package com.ford.javatest.repos;

import com.ford.javatest.modal.Product;

import java.util.List;

public interface ProductRepo {

    public Product getProductById(String productId);

    public List<Product> getAllAvailableProducts();
}
