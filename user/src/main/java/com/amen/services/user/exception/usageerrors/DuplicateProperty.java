package com.amen.services.user.exception.usageerrors;

public class DuplicateProperty extends UsageException {
    private final static String MESSAGE = "Entity with given '%s' already exists.";

    public DuplicateProperty(String fieldName) {
        super(String.format(MESSAGE, fieldName));
    }

    public DuplicateProperty(String fieldName, String referTo) {
        super(String.format(MESSAGE, fieldName), referTo);
    }
}
