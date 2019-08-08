package com.amen.services.user.exception.usageerrors;

public class UsageException extends RuntimeException {
    protected String referTo;

    public UsageException(String message) {
        super(message);
    }

    public UsageException(String message, String referTo) {
        super(message);
        this.referTo = referTo;
    }

    public String getReferTo() {
        return referTo;
    }
}
