package com.project.demo.controllers;

import com.project.demo.dtos.OrderItemDto;
import com.project.demo.dtos.SoupLightDto;
import com.project.demo.models.Order;

import com.project.demo.services.OrderService;
import com.project.demo.services.SoupService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@CrossOrigin
public class OrderController {
    private final OrderService orderService;
    private final SoupService soupService;

    @Autowired
    public OrderController(OrderService orderService, SoupService soupService) {
        this.orderService = orderService;
        this.soupService = soupService;
    }

    @GetMapping("/orders")
    public String getOrderPage() {
        System.out.println("Accessing orders page");
        return "orders-page";
    }

    @GetMapping("/getStartedOrder/{userId}")
    public String getStartedOrder(@PathVariable Integer userId, Model model) {
        System.out.println("Getting started order for user with ID: " + userId);
        Order startedOrder = orderService.getStartedOrder(userId);
        List<SoupLightDto> soupsList = soupService.getAll();
        OrderItemDto orderItemDto = new OrderItemDto();

        model.addAttribute("soups", soupsList);
        model.addAttribute("order", startedOrder);
        model.addAttribute("orderItemDto", orderItemDto);
        return "order-details";
    }

    @PostMapping("/addSoupToOrder/{userId}")
    public String addSoupToOrder(@PathVariable Integer userId, @ModelAttribute("orderItemDto") OrderItemDto orderItemDto) {
        System.out.println("Adding soup to order for user with ID: " + userId + ", Soup ID: " + orderItemDto.getSoupId() );
        System.out.println("Quantity: " + orderItemDto.getQuantity() );
        orderService.addItemToOpenOrder(userId, orderItemDto);
        return "redirect:/getStartedOrder/" + userId;
    }
}
