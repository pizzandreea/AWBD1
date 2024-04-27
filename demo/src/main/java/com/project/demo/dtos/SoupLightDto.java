package com.project.demo.dtos;


import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;

import java.util.List;

public class SoupLightDto {
    private Integer id;
    private String name;
    private double price;
    private int stock;
    private SoupType type;

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public SoupType getType() {
        return type;
    }

    public void setType(SoupType type) {
        this.type = type;
    }

    public Soup toSoup(Soup soup){
        soup.setName(this.getName());
        soup.setPrice(this.getPrice());
        soup.setStock(this.getStock());
        soup.setType(this.getType());
        return soup;
    }

}
