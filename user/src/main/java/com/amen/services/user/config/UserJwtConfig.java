package com.amen.services.user.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJwtConfig {
    @Value("${security.jwt.user.uriLogin:/auth/login}")
    private String uriLogin;

    @Value("${security.jwt.user.uriRegister:/auth/register}")
    private String uriRegister;

    @Value("${security.jwt.user.header:Authorization}")
    private String header;

    @Value("${security.jwt.user.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.user.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.jwt.user.secret:JwtSecretKey}")
    private String secret;

    @Value("${security.jwt.user.allowedIpTag:allowedIp}")
    private String allowedIpTag;

    @Value("${security.jwt.user.authoritiesTag:authorities}")
    private String authoritiesTag;

    @Value("${security.jwt.user.loginClaimTag:claimlogin}")
    private String loginClaimTag;

    @Value("${security.jwt.user.passwordClaimTag:claimpassword}")
    private String passwordClaimTag;

    @Value("${security.jwt.user.uidClaimTag:claimuid}")
    private String uidClaimTag;
}