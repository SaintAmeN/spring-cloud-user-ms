package com.amen.services.user.exception.security;

/**
 * @author amen
 * Date: 11/13/18
 * Time: 10:54 AM
 */
public class UnauthorizedIpCall extends RuntimeException {
    public UnauthorizedIpCall() {
        super("This call has been made from ip address that hasn't been authorized.");
    }
}
