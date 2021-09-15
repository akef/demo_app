package com.demo.jumia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
public class JumiaApplication extends WebMvcConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(JumiaApplication.class, args);
	}
	
	 @Bean
	    public WebMvcConfigurer corsConfigurer() {
	        return new WebMvcConfigurer() {
	            @Override
	            public void addCorsMappings(CorsRegistry registry) {
	                registry.addMapping("/**")
	                        .allowedOrigins("http://localhost:4200")
	                        .allowedMethods("PUT", "DELETE", "GET", "POST")//or allow all as you like
	                        .allowCredentials(true)
	                        .allowedHeaders("Content-Type", "Accept", "Authorization")
	                        .exposedHeaders("header1", "header2");
	            }
	        };
	    }

}
