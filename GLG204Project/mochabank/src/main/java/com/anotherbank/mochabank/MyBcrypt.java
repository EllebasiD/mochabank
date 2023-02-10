package com.anotherbank.mochabank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MyBcrypt {
	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String mdp = bCryptPasswordEncoder.encode("cnam");
		System.out.println(mdp);
		System.out.println(bCryptPasswordEncoder.matches("cnam", "$2a$10$CA9/TGm5pzZaZ30otLWgeukjr0EF8NYpTK3Oha/oB4zM7EeG/JhAK"));
	}
}
