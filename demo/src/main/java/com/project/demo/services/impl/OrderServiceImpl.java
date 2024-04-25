package com.project.demo.services.impl;

import com.project.demo.services.OrderService;

import com.project.demo.dtos.OrderItemDto;
import com.project.demo.exceptions.EntityNotFoundException;
import com.project.demo.models.*;
import com.project.demo.repositories.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderHeaderRepository orderHeaderRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final SoupRepository soupRepository;
    private final OrderItemRepository orderItemRepository;
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                        OrderHeaderRepository orderHeaderRepository, ShippingAddressRepository shippingAddressRepository,
                        SoupRepository soupRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderHeaderRepository = orderHeaderRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.soupRepository = soupRepository;
        this.orderItemRepository = orderItemRepository;
    }


    @Override
    public Order getStartedOrder(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            var openOrder = orderRepository.findOrderStartedByUserAndStatus(user, OrderStatus.STARTED);
            if (openOrder == null) {
                Order newOrder = new Order();
                newOrder.setUser(user);
                newOrder.setOrderItems(new ArrayList<>());
                newOrder.setOrderDate(new Date());
                newOrder.setStatus(OrderStatus.STARTED);

                ShippingAddress newShippingAddress = new ShippingAddress();
                ShippingAddress savedShippingAddress = shippingAddressRepository.save(newShippingAddress);

                OrderHeader newOrderHeader = new OrderHeader();
                newOrderHeader.setShippingAddress(savedShippingAddress);
                OrderHeader savedOrderHeader = orderHeaderRepository.save(newOrderHeader);
                newOrder.setOrderHeader(savedOrderHeader);

                return orderRepository.save(newOrder);

            } else {
                return openOrder;
            }
        } else {
            throw new EntityNotFoundException("User with ID " + userId + " not found");
        }
    }

    @Override
    public Order addItemToOpenOrder(Integer userId, OrderItemDto orderItemDto) {
        Order openOrder = getStartedOrder(userId);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(openOrder);

        Soup soup = soupRepository.findById(orderItemDto.getSoupId())
                .orElseThrow(() -> new IllegalArgumentException("Soup not found"));

        orderItem.setSoup(soup);
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(soup.getPrice());

        var savedOrderItem = orderItemRepository.save(orderItem);
        openOrder.addItem(savedOrderItem);
        updateTotalCost(openOrder, openOrder.getOrderHeader());

        return orderRepository.save(openOrder);
    }

    @Override
    public Order removeItemFromOpenOrder(Integer userId, Integer orderItemId) {
        Order openOrder = getStartedOrder(userId);

        OrderItem orderItemToRemove = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found"));

        openOrder.getOrderItems().remove(orderItemToRemove);

        updateTotalCost(openOrder, openOrder.getOrderHeader());

        return orderRepository.save(openOrder);
    }

    @Override
    public void updateTotalCost(Order order, OrderHeader orderHeader) {
        if (order != null && order.getOrderItems() != null) {
            var totalCost = order.getOrderItems().stream()
                    .mapToDouble(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                    .sum();
            orderHeader.setTotalCost(totalCost);
        }
    }

}