package com.galenyoder.spring.security.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MultiAuthDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiAuthDemoApplication.class, args);
	}

	@RequestMapping("/secure")
	public String getSecureMessage() {
		return "secure message";
	}

}
