package com.amen.services.user.component;

import com.amen.services.user.exception.UserNotLoggedInException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

/**
 * @author amen
 * @project ui-ms
 * @created 17.07.19
 */
@Slf4j
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionAuth {

    public SessionAuth() {
        log.error("Session auth created.");
    }

    public Authentication getAuthentication() throws UserNotLoggedInException {
        return loadAuthentication().orElseThrow(UserNotLoggedInException::new);
    }

    private Optional<Authentication> loadAuthentication(){
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
    }
}
