package com.amen.services.user.exception.usageerrors;

public class Duplicate extends UsageException {
    public Duplicate(String message) {
        super(message);
    }

    public Duplicate(String message, String referTo) {
        super(message, referTo);
    }
}
