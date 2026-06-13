package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;

import jakarta.validation.Valid;

@Controller
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String getProducts(Model model) {

        model.addAttribute(
                "products",
                service.findAll());

        return "products";
    }
    @GetMapping("/products/new")
    public String showForm(Model model) {

        model.addAttribute(
                "product",
                new Product());

        return "product-form";
    }
    @PostMapping("/products")
    public String saveProduct(
            @Valid Product product,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if(result.hasErrors()) {
            return "product-form";
        }

        service.save(product);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Product added successfully");

        return "redirect:/products";
    }
    @GetMapping("/products/edit/{id}")
    public String showEditForm(
            @PathVariable Long id,
            Model model) {

        Product product =
                service.findById(id);

        model.addAttribute(
                "product",
                product);

        return "product-form";
    }
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        service.delete(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Product deleted successfully");

        return "redirect:/products";
    }
}
