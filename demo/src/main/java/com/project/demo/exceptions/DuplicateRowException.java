package com.project.demo.exceptions;

public class DuplicateRowException extends Exception {
    public DuplicateRowException(String entityName, String propertyName, String propertyValue) {
        super(String.format("Instance of %s with %s = %s already exists in the database",
                entityName,
                propertyName,
                propertyValue));
    }

}