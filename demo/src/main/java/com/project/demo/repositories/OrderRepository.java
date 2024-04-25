package com.project.demo.repositories;


import com.project.demo.models.Order;
import com.project.demo.models.OrderStatus;
import com.project.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderStartedByUserAndStatus(User user, OrderStatus orderStatus);
}
