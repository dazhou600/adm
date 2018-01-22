package com.bgcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
@EnableCaching
public class AdmApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdmApplication.class, args);
	}
}
