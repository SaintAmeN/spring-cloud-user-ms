package com.aps.services.user.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author amen
 * Date: 11/13/18
 * Time: 12:59 PM
 */
@Component
public class TokenUtility {
    private final static String ISSUER_UUID = "AUTH_USER_ISSUER_CyView";

    @Autowired
    private UserJwtConfig userJwtConfig;

    @Autowired
    private HttpServletRequest request;

    public String generateToken(Authentication auth) {
        Long now = System.currentTimeMillis();
        String token = Jwts.builder()
                .setIssuer(ISSUER_UUID)
                .setSubject(auth.getName())

                .claim(userJwtConfig.getAuthoritiesTag(), auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))

                .claim(userJwtConfig.getAllowedIpTag(), request.getRemoteAddr())

                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + userJwtConfig.getExpiration() * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, userJwtConfig.getSecret().getBytes())
                .compact();
        return token;
    }

    public Claims parse(@NotNull String token) {
        return Jwts.parser()
                .setSigningKey(userJwtConfig.getSecret().getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
