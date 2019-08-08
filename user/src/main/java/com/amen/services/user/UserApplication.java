package com.amen.services.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"com.aps.services.user", "com.aps.services.config", "com.aps.services.service", "com.aps.services.component"})
@EnableDiscoveryClient
public class UserApplication {

    public static void main(String[] args) {
        log.warn("Var: " + System.getProperty("machine"));
        SpringApplication.run(UserApplication.class, args);
    }

}
