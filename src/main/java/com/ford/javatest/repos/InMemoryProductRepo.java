package com.ford.javatest.repos;

import com.ford.javatest.modal.Product;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Repository
@NoArgsConstructor
public class InMemoryProductRepo implements ProductRepo {

    private List<Product> stock;

    @PostConstruct
    public void initializeProducts() {
        stock = Arrays.asList(
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
    }


    @Override
    public Product getProductById(String productId) {
        return stock.stream()
                .filter(product -> product.getProductId().equalsIgnoreCase(productId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> getAllAvailableProducts() {
        return stock;
    }
}
