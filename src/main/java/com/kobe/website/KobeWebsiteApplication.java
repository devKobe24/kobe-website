package com.kobe.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KobeWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(KobeWebsiteApplication.class, args);
    }

}
