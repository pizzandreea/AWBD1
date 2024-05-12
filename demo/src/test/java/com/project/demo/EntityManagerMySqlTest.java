package com.project.demo;

import com.project.demo.models.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("testing")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class EntityManagerMySqlTest {
    @Autowired
    private EntityManager entityManager;

    @Test
    public void createAndFindOrder() {
        Order order = new Order();
        order.setStatus(OrderStatus.STARTED);
        order.setOrderDate(new Date());

        User user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("Testurescu");
        user.setLastName("Test");
        entityManager.persist(user);

        order.setUser(user);

        Soup soup = new Soup();
        entityManager.persist(soup);
        entityManager.flush();

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrder(order);
        orderItem1.setSoup(soup);
        orderItem1.setPrice(10);
        orderItem1.setQuantity(1);
        orderItems.add(orderItem1);

        order.setOrderItems(orderItems);

        entityManager.persist(order);
        entityManager.flush();
        entityManager.clear();

        Order foundOrder = entityManager.find(Order.class, order.getId());

        assertNotNull(foundOrder);
        assertEquals(OrderStatus.STARTED, foundOrder.getStatus());
        assertEquals(user.getId(), foundOrder.getUser().getId());
        assertEquals(1, foundOrder.getOrderItems().size());
    }



    @Test
    public void updateOrderStatus() {
        Order order = new Order();
        order.setStatus(OrderStatus.STARTED);
        order.setOrderDate(new Date());

        entityManager.persist(order);
        entityManager.flush();
        entityManager.clear();

        Order foundOrder = entityManager.find(Order.class, order.getId());

        foundOrder.setStatus(OrderStatus.COMPLETED);

        entityManager.flush();
        entityManager.clear();

        Order updatedOrder = entityManager.find(Order.class, order.getId());

        assertEquals(OrderStatus.COMPLETED, updatedOrder.getStatus());
    }
}
