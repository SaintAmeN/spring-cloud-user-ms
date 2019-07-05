package com.aps.services.user.configuration;

import com.aps.services.user.component.TokenUtility;
import com.aps.services.user.component.UserJwtConfig;
import com.aps.services.user.filters.JwtAuthenticationFilter;
import com.aps.services.user.filters.JwtTokenAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService authorizationService;
    private UserJwtConfig userJwtConfig;
    private PasswordEncoder passwordEncoder;
    private TokenUtility tokenUtility;

    @Autowired
    public SecurityConfiguration(UserDetailsService authorizationService, UserJwtConfig userJwtConfig, PasswordEncoder passwordEncoder, TokenUtility tokenUtility) {
        this.authorizationService = authorizationService;
        this.userJwtConfig = userJwtConfig;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtility = tokenUtility;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), userJwtConfig, tokenUtility))
                .addFilterAfter(new JwtTokenAuthenticationFilter(userJwtConfig, tokenUtility), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, userJwtConfig.getUriLogin()).permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authorizationService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}