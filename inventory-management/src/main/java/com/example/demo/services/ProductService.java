package com.example.demo.services;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }


    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    public Product findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }
}
