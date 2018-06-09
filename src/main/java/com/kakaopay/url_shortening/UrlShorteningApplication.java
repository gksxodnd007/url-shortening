package com.kakaopay.url_shortening;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.kakaopay.url_shortening")
@EntityScan(basePackages = "com.kakaopay.url_shortening.domain")
@EnableAutoConfiguration
public class UrlShorteningApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShorteningApplication.class, args);
	}

}
