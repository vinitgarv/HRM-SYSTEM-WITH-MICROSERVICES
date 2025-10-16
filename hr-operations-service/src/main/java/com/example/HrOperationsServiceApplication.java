package com.example;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;




@OpenAPIDefinition(
		info = @Info(
				title = "HR Operations Management API Collection",
				description = "This API collection provides all endpoints for HR management operations."
		)
)
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
public class HrOperationsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrOperationsServiceApplication.class, args);
	}
}
