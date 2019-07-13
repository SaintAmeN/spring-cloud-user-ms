package com.aps.services.user.exception;

import org.springframework.security.authentication.AccountStatusException;

public class AccountNotActiveException extends AccountStatusException {
    public AccountNotActiveException(String msg) {
        super(msg);
    }
}
