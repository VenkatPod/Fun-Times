package com.ford.javatest.service;

import com.ford.javatest.modal.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

/**
 * This Service is responsible for listing the available products.
 */
@Service
public class ProductService {

    public String getAddProductsMenu(List<Product> listOfProducts) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("------------Add Products Menu-------------\n");
        IntStream.range(1, listOfProducts.size() + 1)
                .forEach(idx -> {
                    stringBuilder
                            .append(idx).append(".").append(listOfProducts.get(idx - 1).getProductId()).append("\n");
                });
        stringBuilder.append("x. Previous Menu\n");
        stringBuilder.append("To add enter corresponding product number or x to go back to main menu :");
        return stringBuilder.toString();
    }

    public Product getProductFromUserInput(int input, List<Product> listOfProduct) {
        return (input-1 > -1 && input-1 < listOfProduct.size()) ? listOfProduct.get(input-1) : null;
    }
}
