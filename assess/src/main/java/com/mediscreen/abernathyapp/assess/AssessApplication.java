package com.mediscreen.abernathyapp.assess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AssessApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssessApplication.class, args);
    }

}