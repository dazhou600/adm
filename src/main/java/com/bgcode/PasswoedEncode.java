package com.bgcode;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class PasswoedEncode {

	public static void main(String[] args) {
		String[] r = {"41b7f7b6071e65abff59bc91619482b0bead71328e4c47bb2b637ce9fa5a99cd3445877b24847cdd","bf57bf7c9a009ee138318cbf5cabafc57330fe7b42e902ee6921a19f772381579f2e0692405c766b"};
		for (int i = 0; i < r.length; i++) {
			//r = new StandardPasswordEncoder("wztq").encode("123456");
			//System.out.println("nononononononononono:" + r);
			boolean b =  new StandardPasswordEncoder("wztq").matches("123456", r[i]) ;
			System.out.println("nononononononononono:" + b);

		}
	}
}
