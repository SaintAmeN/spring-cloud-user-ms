package com.amen.services.user.exception.usageerrors;

public class ConfirmPasswordException extends UsageException {
    private final static String MESSAGE = "Confirm password and password are not the same.";
    public ConfirmPasswordException() {
        super(MESSAGE);
    }

    public ConfirmPasswordException(String referTo) {
        super(MESSAGE, referTo);
    }
}
