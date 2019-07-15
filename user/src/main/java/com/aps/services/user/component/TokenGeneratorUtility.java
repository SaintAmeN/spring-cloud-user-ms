package com.aps.services.user.component;

import com.aps.services.config.UserJwtConfig;
import com.aps.services.user.model.domain.Employee;
import com.aps.services.user.repository.EmployeeRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aps.services.component.TokenParserUtility.ISSUER_UUID;

/**
 * @author amen
 * Date: 11/13/18
 * Time: 12:59 PM
 */
@Component
@RequiredArgsConstructor
public class TokenGeneratorUtility {
    private final UserJwtConfig userJwtConfig;
    private final EmployeeRepository employeeRepository;

    @Autowired
    private HttpServletRequest request;

    public String generateToken(Authentication auth) {
        Long now = System.currentTimeMillis();

        User upat = ((org.springframework.security.core.userdetails.User) auth.getPrincipal());
        Optional<Employee> employeeOptional = employeeRepository.findByUsername(upat.getUsername());
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();

            String token = Jwts.builder()
                    .setIssuer(ISSUER_UUID)
                    .setSubject(auth.getName())

                    .claim(userJwtConfig.getAuthoritiesTag(), auth.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))

                    .claim(userJwtConfig.getAllowedIpTag(), request.getRemoteAddr())
                    .claim(userJwtConfig.getLoginClaimTag(), employee.getUsername())
                    .claim(userJwtConfig.getPasswordClaimTag(), Base64.getEncoder().encodeToString(employee.getPassword().getBytes()))
                    .claim(userJwtConfig.getUidClaimTag(), employee.getId())

                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(now + userJwtConfig.getExpiration() * 1000))  // in milliseconds
                    .signWith(SignatureAlgorithm.HS512, userJwtConfig.getSecret().getBytes())
                    .compact();
            return token;
        }
        throw new EntityNotFoundException("User not found :(");
    }
}
