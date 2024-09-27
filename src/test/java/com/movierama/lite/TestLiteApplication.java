package com.movierama.lite;

import org.springframework.boot.SpringApplication;

public class TestLiteApplication {

	public static void main(String[] args) {
		SpringApplication.from(MovieRamaLiteApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
