package com.internship.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internship.ecommerce.modal.Product;
import com.internship.ecommerce.repo.ProductRepository;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product addProduct(Product product) {
        product.setCreatedAt(new Date());
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        Product existingProduct = getProductById(id);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setUpdatedAt(new Date());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
