package com.vvachev.movielibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MovielibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovielibraryApplication.class, args);
	}

}
