package com.example.demo.entity;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import static org.junit.jupiter.api.Assertions.*;

class ProductValidationTest {

    private final Validator validator =
            Validation.buildDefaultValidatorFactory()
                    .getValidator();

    @Test
    void shouldFailWhenNameIsBlank() {

        Product product = new Product();

        product.setSku("ABC");
        product.setName("");
        product.setQuantity(10);
        product.setPrice(100.0);

        Set<ConstraintViolation<Product>> violations =
                validator.validate(product);

        assertFalse(
                violations.isEmpty());
    }
}