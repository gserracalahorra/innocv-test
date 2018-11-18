package com.innocv.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class UserApiApplication {

    public static void main(String [] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }

}