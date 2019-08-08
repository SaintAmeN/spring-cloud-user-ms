package com.amen.services.user.auth;

import com.amen.services.user.component.TokenParserUtility;
import com.amen.services.user.config.UserJwtConfig;
import com.amen.services.user.exception.security.UnauthorizedCall;
import com.amen.services.user.exception.security.UnauthorizedIpCall;
import com.amen.services.user.model.AccountAuthentication;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author amen
 * Date: 11/13/18
 * Time: 10:36 AM
 */
@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenParserUtility tokenUtility;
    private final UserJwtConfig userJwtConfig;

    @Autowired
    public JwtTokenAuthenticationFilter(UserJwtConfig userJwtConfig, TokenParserUtility tokenUtility) {
        this.tokenUtility = tokenUtility;
        this.userJwtConfig = userJwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader(userJwtConfig.getHeader());

        if (header == null || !header.startsWith(userJwtConfig.getPrefix())) {
            chain.doFilter(request, response);        // If not valid, go to the next filter.
            return;
        }

        String token = header.replace(userJwtConfig.getPrefix(), "");

        try {
            Claims claims = tokenUtility.parse(token);

            String username = claims.getSubject();
            if (username != null) {
                if (!claims.containsKey(userJwtConfig.getAuthoritiesTag())) {
                    throw new UnauthorizedCall();
                }

                List<String> authorities = (List<String>) claims.get(userJwtConfig.getAuthoritiesTag());

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        new AccountAuthentication(String.valueOf(claims.get(userJwtConfig.getLoginClaimTag())),
                                String.valueOf(claims.get(userJwtConfig.getPasswordClaimTag())),
                                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                                token,
                                Long.parseLong(String.valueOf(claims.get(userJwtConfig.getUidClaimTag())))),
                        null,
                        authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

            if (claims.containsKey(userJwtConfig.getAllowedIpTag())) {
                Object passedIpObj = claims.get(userJwtConfig.getAllowedIpTag());
                if (!(passedIpObj instanceof String)) {
                    throw new UnauthorizedIpCall();
                }

                String passedIp = String.valueOf(passedIpObj);
                if (!passedIp.equals(request.getRemoteAddr())) {
                    throw new UnauthorizedIpCall();
                }
            } else {
                throw new UnauthorizedIpCall();
            }

        } catch (UnauthorizedIpCall ue) {
            log.error("Unauthorized request.", ue);
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            log.warn("Authentication exception, user is unauthenticated.", e);
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }
}
