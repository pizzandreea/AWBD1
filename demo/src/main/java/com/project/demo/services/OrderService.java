package com.project.demo.services;

import com.project.demo.dtos.OrderItemDto;
import com.project.demo.models.Order;
import com.project.demo.models.OrderHeader;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public Order getStartedOrder(Integer userId);
    public Order addItemToOpenOrder(Integer userId, OrderItemDto orderItemDto);
    public Order removeItemFromOpenOrder(Integer userId, Integer orderItemId);
    public void updateTotalCost(Order order, OrderHeader orderHeader);
}
