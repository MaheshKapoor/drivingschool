package com.search.drivingschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.*;

@EnableAutoConfiguration
@SpringBootApplication
public class DrivingschoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrivingschoolApplication.class, args);
	}
}
