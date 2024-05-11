package com.project.demo.exceptions;

public class RemoveItemFromOrderException extends RuntimeException {
    public RemoveItemFromOrderException(String message) {
        super("An error occurred while removing the soup from the order: " + message);
    }
}