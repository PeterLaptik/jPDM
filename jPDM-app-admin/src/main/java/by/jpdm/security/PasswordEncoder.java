package by.jpdm.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface PasswordEncoder {
	String encode(CharSequence rawPassword) throws NoSuchAlgorithmException, InvalidKeySpecException;

	boolean matches(CharSequence rawPassword, String encodedPassword)
			throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException;
}
