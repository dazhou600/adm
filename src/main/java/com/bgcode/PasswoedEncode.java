package com.bgcode;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswoedEncode {

	public static void main(String[] args) {
		String r = new StandardPasswordEncoder("wztq").encode("123456");
		System.out.println("nononononononononono:"+r);
	}

}
