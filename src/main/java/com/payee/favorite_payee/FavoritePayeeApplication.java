package com.payee.favorite_payee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class FavoritePayeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FavoritePayeeApplication.class, args);
	}

}
