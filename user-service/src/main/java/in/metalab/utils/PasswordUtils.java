package in.metalab.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
	
	public static String encodePassword(String rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}
	
	public static boolean isMatched(String rawPassword, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
	}

}
