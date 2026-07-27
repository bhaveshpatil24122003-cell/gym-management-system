package com.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync; // Added
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync // Added to support background email sending
public class GymManagement {

    public static void main(String[] args) {
        SpringApplication.run(GymManagement.class, args);
    }
}