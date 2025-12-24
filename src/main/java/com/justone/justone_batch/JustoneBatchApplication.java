package com.justone.justone_batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JustoneBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(JustoneBatchApplication.class, args);
	}

}
