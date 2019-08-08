package com.amen.services.user.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AccountAuthentication extends User {
    @Getter
    private String token;
    @Getter
    private Long uid;

    public AccountAuthentication(String username, String password, Collection<? extends GrantedAuthority> authorities, String token, Long uid) {
        super(username, password, authorities);
        this.token = token;
        this.uid = uid;
    }

    public AccountAuthentication(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String token, Long uid) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.token = token;
        this.uid = uid;
    }
}
