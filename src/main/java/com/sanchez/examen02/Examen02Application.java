package com.sanchez.examen02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class Examen02Application {

	public static void main(String[] args) {
		SpringApplication.run(Examen02Application.class, args);
	}

}
