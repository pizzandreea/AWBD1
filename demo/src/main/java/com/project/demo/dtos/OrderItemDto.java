package com.project.demo.dtos;

public class OrderItemDto {

    private Integer soupId;
    private int quantity;

    public OrderItemDto(Integer soupId, int quantity){
        this.soupId = soupId;
        this.quantity = quantity;
    }

    public OrderItemDto(){};

    public Integer getSoupId() {
        return soupId;
    }

    public void setSoupId(Integer soupId) {
        this.soupId = soupId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
