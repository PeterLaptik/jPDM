package by.jpdm;

import org.junit.Test;

import by.jpdm.security.ElytronBCryptEncoder;
import by.jpdm.security.PasswordEncoder;

import static org.junit.Assert.assertTrue;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class PasswordsTest {

    @Test
    public void test() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
    	String pass = "Pass123";
    	String badPass = "Pass12";
    	PasswordEncoder encoder = new ElytronBCryptEncoder();
    	String resultFirst = encoder.encode(pass);
    	System.out.println(pass + ": " + resultFirst);
    	String resultSecond = encoder.encode(pass);
    	System.out.println(pass + ": " + resultSecond);
    	String oddResult = encoder.encode(badPass);
    	System.out.println(badPass + ": " + oddResult);
    	
    	System.out.println("Good: " + encoder.matches(pass, resultFirst));
    	System.out.println("Good: " + encoder.matches(pass, resultSecond));
    	System.out.println("Bad: " + encoder.matches(badPass, resultFirst));
    	System.out.println("Bad: " + encoder.matches(badPass, resultSecond));
    	System.out.println("Good: " + encoder.matches(badPass, oddResult));
    	System.out.println("Bad: " + encoder.matches(pass, oddResult));
        assertTrue(true);
    }
}
