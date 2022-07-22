package com.lawencon.community.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GenerateCode {
	public String generate() {
		String alphaNum = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		Random random = new Random();
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 5; i++) {
			sb.append(alphaNum.charAt(random.nextInt(alphaNum.length())));
		}
		
		return sb.toString();
	}
}
