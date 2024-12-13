package com.internship.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.internship.ecommerce.modal.Order;
import com.internship.ecommerce.modal.OrderItem;
import com.internship.ecommerce.modal.OrderRequest;
import com.internship.ecommerce.modal.Product;
import com.internship.ecommerce.repo.OrderRepository;
import com.internship.ecommerce.repo.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Method to get all orders
    public List<Order> getAllOrders() {
        return mongoTemplate.findAll(Order.class);
    }

    // Method to get order by id
    public Order getOrderById(String id) {
        return mongoTemplate.findById(id, Order.class);
    }

    // Method to create a new order
    public Order createOrder(Order order) {
        // Save the order using MongoTemplate
        return mongoTemplate.save(order);
    }

    // Method to update an existing order
    public Order updateOrder(String id, Order order) {
        // First, find the order by its ID
        Order existingOrder = mongoTemplate.findById(id, Order.class);
        if (existingOrder != null) {
            existingOrder.setUserId(order.getUserId());
            existingOrder.setTotalAmount(order.getTotalAmount());
            existingOrder.setStatus(order.getStatus());
            existingOrder.setItems(order.getItems());
            // Save the updated order
            return mongoTemplate.save(existingOrder);
        }
        return null; // Or throw exception if order not found
    }

    // Method to delete an order
    public void deleteOrder(String id) {
        Order order = mongoTemplate.findById(id, Order.class);
        if (order != null) {
            mongoTemplate.remove(order);
        }
    }
}