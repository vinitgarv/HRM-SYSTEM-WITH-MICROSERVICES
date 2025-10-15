package com.moonstack;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@OpenAPIDefinition(
		info = @Info(
				title = "Payroll-Service Operations Management API Collection",
				description = "This API collection provides all endpoints for Payroll-Service management operations."
		)
)
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PayrollServiceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(PayrollServiceApplication.class, args);
	}
}
