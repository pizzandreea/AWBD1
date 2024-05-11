package com.project.demo.dtos;


import com.project.demo.models.Soup;
import com.project.demo.models.SoupType;
import jakarta.validation.constraints.*;

import java.util.List;

public class SoupCreateDto {
    @NotBlank
    private String name;
    @Positive
    private double price;
    @PositiveOrZero
    private int stock;
    private List<String> ingredients;
    @NotNull
    private SoupType type;

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

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
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
        soup.setIngredients(this.getIngredients());
        soup.setType(this.getType());
        return soup;
    }

}
