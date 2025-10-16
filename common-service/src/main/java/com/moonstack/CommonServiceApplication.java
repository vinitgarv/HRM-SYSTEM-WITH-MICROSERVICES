package com.moonstack;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@OpenAPIDefinition(
		info = @Info(
				title = "Common-Service Operations Management API Collection",
				description = "This API collection provides all endpoints for Common-Service management operations."
		)
)
@SpringBootApplication
@EnableDiscoveryClient
public class CommonServiceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(CommonServiceApplication.class, args);
	}
}
