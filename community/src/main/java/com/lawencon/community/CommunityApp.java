package com.lawencon.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lawencon"})
public class CommunityApp {
	public static void main(String[] args) {
		SpringApplication.run(CommunityApp.class, args);
	}
}
