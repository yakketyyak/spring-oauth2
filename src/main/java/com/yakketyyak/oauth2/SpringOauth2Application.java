package com.yakketyyak.oauth2;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringOauth2Application {

	private final PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringOauth2Application.class, args);
	}

	@PostConstruct
	public void test() {
		System.out.println("		encoder. " + encoder.encode("123"));
	}

}
