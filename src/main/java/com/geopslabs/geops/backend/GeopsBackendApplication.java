package com.geopslabs.geops.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GeopsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeopsBackendApplication.class, args);
    }

}
