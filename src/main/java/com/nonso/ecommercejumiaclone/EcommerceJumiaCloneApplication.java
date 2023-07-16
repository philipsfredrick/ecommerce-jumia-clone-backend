package com.nonso.ecommercejumiaclone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@EnableJpaAuditing
@SpringBootApplication
public class EcommerceJumiaCloneApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(EcommerceJumiaCloneApplication.class, args);
    }
}
