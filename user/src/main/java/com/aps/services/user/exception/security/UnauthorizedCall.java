package com.aps.services.user.exception.security;

/**
 * @author amen
 * Date: 11/13/18
 * Time: 1:21 PM
 */
public class UnauthorizedCall extends RuntimeException {
    public UnauthorizedCall() {
        super("This user has no authorities.");
    }
}
