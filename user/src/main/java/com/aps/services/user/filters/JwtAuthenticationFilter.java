package com.aps.services.user.filters;

import com.aps.services.model.dto.userservice.requests.AuthenticationRequestDto;
import com.aps.services.model.dto.userservice.responses.AuthenticationResponse;
import com.aps.services.user.component.TokenUtility;
import com.aps.services.user.component.UserJwtConfig;
import com.aps.services.user.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.stream.Collectors;


/**
 * @author amen
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;

    private final UserJwtConfig userJwtConfig;
    private final TokenUtility tokenUtility;
    private final ObjectMapper objectMapper;
    private final EmployeeService employeeService;

    public JwtAuthenticationFilter(AuthenticationManager authManager, UserJwtConfig userJwtConfig, TokenUtility tokenUtility, ObjectMapper objectMapper, EmployeeService employeeService) {
        this.authManager = authManager;
        this.userJwtConfig = userJwtConfig;
        this.tokenUtility = tokenUtility;
        this.objectMapper = objectMapper;
        this.employeeService = employeeService;

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
        User u = (User) auth.getPrincipal();

        PrintWriter writer = response.getWriter();
        objectMapper.writeValue(writer, new AuthenticationResponse(
                String.valueOf(u.getUsername()),
                String.valueOf(u.getPassword()),
                u.getAuthorities().stream().map(author -> author.getAuthority()).collect(Collectors.toList())));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        writer.flush();

    }

    private String combineToken(String prefix, String token) {
        if (prefix.endsWith(" ")) {
            return (prefix + token).replaceAll("  ", " ");
        }
        return (prefix + " " + token).replaceAll("  ", " ");
    }
}

