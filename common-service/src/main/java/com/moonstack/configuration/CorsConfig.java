package com.moonstack.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // Allow CORS for all endpoints
						.allowedOrigins("*") // Allow all origins (or specify domains)
						.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // HTTP methods
						.allowedHeaders("*") // Allow all headers
						.allowCredentials(false) // Disallow credentials (set to true if needed)
						.maxAge(3600);
			}
		};
	}
}
