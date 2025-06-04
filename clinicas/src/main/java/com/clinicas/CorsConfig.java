package com.clinicas;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/**")
	            .allowedOrigins("https://30bb-179-51-178-244.ngrok-free.app") // ngrok URL
	            .allowedMethods("*")
	            .allowedHeaders("*");
	}

}
