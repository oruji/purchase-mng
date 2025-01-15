package io.github.oruji.purchasemng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class PurchaseMngApplication {

	public static void main(String[] args) {
		SpringApplication.run(PurchaseMngApplication.class, args);
	}

}
