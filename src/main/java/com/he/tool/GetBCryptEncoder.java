package com.he.tool;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GetBCryptEncoder {

	public static String getBCryptEncoderWord(String word) {

		
		return new BCryptPasswordEncoder().encode(word);	
	}
}
