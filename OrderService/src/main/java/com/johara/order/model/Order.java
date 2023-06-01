package com.johara.order.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orderId;

    private Long customerId;
    private String customerEmail;

    private Long productId;

    private int quantity;
    private String orderStatus;
    private LocalDateTime orderDate;

    // Constructors

    public Order() {
    }

    public Order(String orderId, Long customerId, Long productId, int quantity, LocalDateTime orderDate,
            String customerEmail) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.customerEmail = customerEmail;
        this.orderStatus = "active";
    }

    // Getters and Setters

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String _email) {
        this.customerEmail = _email;
    }

    public String getStatus() {
        return orderStatus;
    }

    public void setStatus(String status) {
        this.orderStatus = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
