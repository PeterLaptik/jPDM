package model.service;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import model.beans.org.User;

//@Component
public class UserServiceDefault implements UserService {
	private static final int SALT_LENGTH = 16;
	final Random random = new SecureRandom();
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(String login, String name, String password) {
		User user = new User(login, name);
		String salt = generateStaticSymbolicSalt();
		user.setSalt(salt);
		String encodedPassword = passwordEncoder.encode(password + salt);
		user.setPassword(encodedPassword);
		return user;
	}

	private String generateStaticSymbolicSalt() {
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		while (counter < SALT_LENGTH) {
			int val = random.nextInt(123);
			if (((val < 123) && (val > 96)) || ((val < 91) && (val > 64)) || ((val < 58) && (val > 47))) {
				counter++;
				sb.append(((char) val));
			}
		}
		return sb.toString();
	}
}
