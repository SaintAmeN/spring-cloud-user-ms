package com.aps.services.user.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class MsTokenConfig {
    @Value("${ms.communication.token.username:sirc}")
    private String password;

    @Value("${ms.communication.token.password:sirc}")
    private String username;

}
