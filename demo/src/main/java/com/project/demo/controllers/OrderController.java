package com.project.demo.controllers;

import com.project.demo.dtos.OrderItemDto;
import com.project.demo.models.Order;

import com.project.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public String getOrderPage(){
        return "orders-page";
    }

    @GetMapping("/getStartedOrder/{userId}")
    public String getStartedOrder(@PathVariable Integer userId, Model model) {
        Order startedOrder = orderService.getStartedOrder(userId);

        model.addAttribute("order", startedOrder);
        return "order-details";
    }


    @GetMapping("/addItemToOpenOrder/{userId}")
    public String addItemToOpenOrderForm(Model model, @PathVariable Integer userId) {
        OrderItemDto orderItemDto = new OrderItemDto();
        model.addAttribute("orderItemDto", orderItemDto);
        model.addAttribute("userId", userId);
        return "add-item-to-open-order";
    }

    @PostMapping("/addItemToOpenOrder/{userId}")
    public String addItemToOpenOrder(@ModelAttribute("orderItemDto") OrderItemDto orderItemDto, @PathVariable Integer userId, Model model) {
            Order updatedOrder = orderService.addItemToOpenOrder(userId, orderItemDto);
            model.addAttribute("updatedOrder", updatedOrder);
            return "redirect:/orders";
    }




}
