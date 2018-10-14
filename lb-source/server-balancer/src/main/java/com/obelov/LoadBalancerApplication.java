package com.obelov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
@EnableCaching
public class LoadBalancerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoadBalancerApplication.class, args);
	}
}
