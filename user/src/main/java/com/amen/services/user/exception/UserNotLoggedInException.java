package com.aps.services.user.exception;

public class UserNotLoggedInException extends Exception {
    public UserNotLoggedInException(String message) {
        super(message);
    }

    public UserNotLoggedInException() {
    }
}