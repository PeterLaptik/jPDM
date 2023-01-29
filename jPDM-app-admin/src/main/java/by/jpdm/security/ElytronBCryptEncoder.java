package by.jpdm.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.WildFlyElytronPasswordProvider;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.spec.EncryptablePasswordSpec;
import org.wildfly.security.password.spec.IteratedSaltedPasswordAlgorithmSpec;
import org.wildfly.security.password.util.ModularCrypt;

public class ElytronBCryptEncoder implements PasswordEncoder {
	private static final int ITERATION_COUNT = 10;
	private Provider provider = new WildFlyElytronPasswordProvider();

	@Override
	public String encode(CharSequence rawPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
		PasswordFactory passwordFactory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, provider);

		byte[] salt = new byte[BCryptPassword.BCRYPT_SALT_SIZE];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);

		IteratedSaltedPasswordAlgorithmSpec iteratedAlgorithmSpec = new IteratedSaltedPasswordAlgorithmSpec(
				ITERATION_COUNT, salt);
		EncryptablePasswordSpec encryptableSpec = new EncryptablePasswordSpec(rawPassword.toString().toCharArray(),
				iteratedAlgorithmSpec);

		BCryptPassword original = (BCryptPassword) passwordFactory.generatePassword(encryptableSpec);
		return ModularCrypt.encodeAsString(original);
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		PasswordFactory passwordFactory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, provider);
		Password userPasswordDecoded = ModularCrypt.decode(encodedPassword);
		Password userPasswordRestored = passwordFactory.translate(userPasswordDecoded);
		return passwordFactory.verify(userPasswordRestored, rawPassword.toString().toCharArray());
	}

}
