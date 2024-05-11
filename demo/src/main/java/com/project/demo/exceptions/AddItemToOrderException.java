package com.project.demo.exceptions;

public class AddItemToOrderException extends RuntimeException {
    public AddItemToOrderException(String message) {
        super("An error occurred while adding the soup to the order: " + message);
    }
}