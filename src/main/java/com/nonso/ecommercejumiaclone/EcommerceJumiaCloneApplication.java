package com.nonso.ecommercejumiaclone;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nonso.ecommercejumiaclone.service.CartService;
import com.nonso.ecommercejumiaclone.utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@EnableJpaAuditing
@SpringBootApplication
public class EcommerceJumiaCloneApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(EcommerceJumiaCloneApplication.class, args);
    }

}
