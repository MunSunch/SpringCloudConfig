package com.munsun.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TestApplication {
	@Value("${prop.value}")
	String value;

	@GetMapping("/")
	public String index() {
		return value;
	}

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

}
