package com.internship.ecommerce.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.internship.ecommerce.modal.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    @Query("{ 'customerName': ?0 }")
    List<Order> findByCustomerName(String customerName);
}

