package com.guen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "userAuditorAware")
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		System.out.println("서버수정");
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("로컬수정");
	}

}
