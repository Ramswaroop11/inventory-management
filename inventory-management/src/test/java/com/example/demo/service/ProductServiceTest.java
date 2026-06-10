package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.ProductService;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.context.ActiveProfiles;
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void shouldSaveProduct() {

        Product product = new Product();

        product.setSku("LAP001");
        product.setName("Laptop");
        product.setQuantity(10);
        product.setPrice(50000.0);

        when(repository.save(any(Product.class)))
                .thenReturn(product);

        Product saved =
                service.save(product);

        assertNotNull(saved);

        assertEquals(
                "Laptop",
                saved.getName());

        verify(repository, times(1))
                .save(product);
    }
}
