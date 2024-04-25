package com.project.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class OrderHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double totalCost;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // Added orphanRemoval
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddress shippingAddress;

    public OrderHeader() {
        this.totalCost = 0;
        this.shippingAddress = new ShippingAddress();
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

}