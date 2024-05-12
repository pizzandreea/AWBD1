package com.project.demo.controllers;

import com.project.demo.dtos.OrderItemDto;
import com.project.demo.dtos.SoupLightDto;
import com.project.demo.models.Order;

import com.project.demo.services.OrderService;
import com.project.demo.services.SoupService;
import groovy.util.logging.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@CrossOrigin
@Slf4j
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SoupService soupService;

    @Autowired
    public OrderController(OrderService orderService, SoupService soupService) {
        this.orderService = orderService;
        this.soupService = soupService;
    }

    @GetMapping("/orders")
    public String getOrderPage() {
        log.info("Accessing orders page");
        return "orders-page";
    }

    @GetMapping("/getStartedOrder/{userId}")
    public String getStartedOrder(@PathVariable Integer userId, Model model) {
        log.info("Getting started order for user with ID: " + userId);
        try {
            Order startedOrder = orderService.getStartedOrder(userId);
            List<SoupLightDto> soupsList = soupService.getAll();
            OrderItemDto orderItemDto = new OrderItemDto();

            model.addAttribute("soups", soupsList);
            model.addAttribute("order", startedOrder);
            model.addAttribute("orderItemDto", orderItemDto);
        }catch(Exception e){
            model.addAttribute("error", e);
            return "error-page";
        }
        return "order-details";
    }

    @PostMapping("/addSoupToOrder/{userId}")
    public String addSoupToOrder(@PathVariable Integer userId, @ModelAttribute("orderItemDto") OrderItemDto orderItemDto, Model model) {
        try{
        log.info("Adding soup to order for user with ID: " + userId + ", Soup ID: " + orderItemDto.getSoupId() );
        log.info("Quantity: " + orderItemDto.getQuantity() );
        orderService.addItemToOpenOrder(userId, orderItemDto);}
        catch(Exception e){
            model.addAttribute("error", e);
            return "error-page";
        }
        return "redirect:/getStartedOrder/" + userId;
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam Integer userId, @RequestParam Integer orderItemId, Model model) {
        try {
            orderService.removeItemFromOpenOrder(userId, orderItemId);
            Order startedOrder = orderService.getStartedOrder(userId);
            List<SoupLightDto> soupsList = soupService.getAll();
            OrderItemDto orderItemDto = new OrderItemDto();

            model.addAttribute("soups", soupsList);
            model.addAttribute("order", startedOrder);
            model.addAttribute("orderItemDto", orderItemDto);
        }catch(Exception e){
            model.addAttribute("error", e);
            return "error-page";
        }

        return "order-details";
    }

}
