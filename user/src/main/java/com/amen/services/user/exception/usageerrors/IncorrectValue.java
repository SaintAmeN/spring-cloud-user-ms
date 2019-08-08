package com.amen.services.user.exception.usageerrors;

public class IncorrectValue extends UsageException {
    private final static String MESSAGE = "Field '%s' has incorrect value.";

    public IncorrectValue(String fieldName) {
        super(String.format(MESSAGE, fieldName));
    }

    public IncorrectValue(String fieldName, String referTo) {
        super(String.format(MESSAGE, fieldName), referTo);
    }
}
