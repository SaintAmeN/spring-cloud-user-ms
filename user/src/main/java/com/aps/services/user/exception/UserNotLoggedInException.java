package com.aps.services.user.exception;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException(String message) {
        super(message);
    }

    public UserNotLoggedInException() {
    }
}