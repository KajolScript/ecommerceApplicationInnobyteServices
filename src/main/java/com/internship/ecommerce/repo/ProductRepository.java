package com.internship.ecommerce.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.internship.ecommerce.modal.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
