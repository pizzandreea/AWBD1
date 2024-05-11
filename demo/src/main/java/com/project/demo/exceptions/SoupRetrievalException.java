package com.project.demo.exceptions;

public class SoupRetrievalException extends RuntimeException {
    public SoupRetrievalException(String message) {
        super("An error occurred while retrieving soups: " + message);
    }
}