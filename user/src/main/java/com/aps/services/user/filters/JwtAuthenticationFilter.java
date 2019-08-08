package com.aps.services.user.filters;

import com.aps.services.user.config.UserJwtConfig;
import com.aps.services.user.model.userservice.requests.AuthenticationRequestDto;
import com.aps.services.user.component.TokenGeneratorUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;


/**
 * @author amen
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;

    private final UserJwtConfig userJwtConfig;
    private final TokenGeneratorUtility tokenUtility;

    public JwtAuthenticationFilter(AuthenticationManager authManager, UserJwtConfig userJwtConfig, TokenGeneratorUtility tokenUtility) {
        this.authManager = authManager;
        this.userJwtConfig = userJwtConfig;
        this.tokenUtility = tokenUtility;

        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(userJwtConfig.getUriLogin(), "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            AuthenticationRequestDto creds = new ObjectMapper().readValue(request.getInputStream(), AuthenticationRequestDto.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    creds.getUsername(), creds.getPassword(), Collections.emptyList());

            return authManager.authenticate(authToken);
        } catch (IOException e) {
            log.error("Error attempting authentication. " + e.getMessage());
            throw new UsernameNotFoundException("Unauthorized");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        String token = tokenUtility.generateToken(auth);

        response.addHeader(userJwtConfig.getHeader(), combineToken(userJwtConfig.getPrefix(), token));
    }

    private String combineToken(String prefix, String token) {
        if (prefix.endsWith(" ")) {
            return (prefix + token).replaceAll("  ", " ");
        }
        return (prefix + " " + token).replaceAll("  ", " ");
    }
}

