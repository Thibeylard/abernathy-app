package com.mediscreen.abernathyapp.patHistory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PatHistoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatHistoryApplication.class, args);
	}

}
