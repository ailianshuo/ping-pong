package com.fluxsample.pong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PongServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PongServiceApplication.class, args);
    }

}
