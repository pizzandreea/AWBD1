package com.project.demo.dtos;


import com.project.demo.models.Order;

import java.util.List;

public class OrderCreateDto {

    private Long userId;
    private List<OrderItemDto> orderItems;

    public Order toOrder(Order order){
        order.setOrderHeader(order.getOrderHeader());
        order.setOrderDate(order.getOrderDate());
        order.setUser(order.getUser());
        order.setOrderItems(order.getOrderItems());
        order.setStatus(order.getStatus());
        return order;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }
}
