package com.appcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AppCenterSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppCenterSpringbootApplication.class, args);
    }

}
