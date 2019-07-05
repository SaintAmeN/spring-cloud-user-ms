package com.aps.services.user.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author amen
 * Date: 11/14/18
 * Time: 12:56 PM
 */
@Slf4j
@Component
public class NetworkConfiguration implements InitializingBean {

    private boolean useIpv4;

    @Value("${services.useipv4}")
    public void setUseIpv4(String useIpv4Setting) {
        log.info("Setting ipv4 to:" + useIpv4Setting);
        try {
            useIpv4 = Boolean.parseBoolean(useIpv4Setting);
        } catch (Exception e) {
            log.error("Fallback with ipv4 not working, setting false.");
            useIpv4 = false;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Setting up ipv4 to: " + useIpv4);
        System.setProperty("java.net.preferIPv4Stack", String.valueOf(useIpv4));
    }
}
