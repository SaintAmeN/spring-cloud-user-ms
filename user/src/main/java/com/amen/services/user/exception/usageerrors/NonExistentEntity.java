package com.amen.services.user.exception.usageerrors;

public class NonExistentEntity extends UsageException {
    private static final String MESSAGE = "This entity does not exist.";

    public NonExistentEntity() {
        super(MESSAGE);
    }

    public NonExistentEntity(String referTo) {
        super(MESSAGE);
        this.referTo = referTo;
    }
}
