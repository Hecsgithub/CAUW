package com.he.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcCongig implements WebMvcConfigurer {

/**
 * 配置类解决跨域
 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// TODO Auto-generated method stub
		registry.addMapping("/**")
		.allowedHeaders("*")
		.allowedMethods("*")
		.maxAge(1800)
		.allowedOrigins("http://localhost:8080");
		 registry.addMapping("/**")
         .allowedOrigins("*")
         .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
         .maxAge(3600)
         .allowCredentials(true);
	}
	
}
