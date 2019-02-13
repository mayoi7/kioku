package com.akira.kioku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author 刘昊楠
 */
@EnableCaching
@SpringBootApplication
public class KiokuApplication {

    public static void main(String[] args) {
        SpringApplication.run(KiokuApplication.class, args);
    }

}

