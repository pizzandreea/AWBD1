package com.project.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class AddItemToOrderException extends RuntimeException {
    public AddItemToOrderException(String message) {
        super("An error occurred while adding the soup to the order: " + message);
    }
}