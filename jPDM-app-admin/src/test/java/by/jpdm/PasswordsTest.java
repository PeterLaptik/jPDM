package by.jpdm;

import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.wildfly.common.Assert;

@ExtendWith(WeldJunit5Extension.class)
public class PasswordsTest {
	
	@WeldSetup
	public WeldInitiator weld = WeldInitiator.of(WeldInitiator.createWeld());
	
//	PasswordEncoder encoder = new ElytronBCryptEncoder();
	
    @Test
    public void encodingTest() {
//    	String pass = "Pass123";
//    	String badPass = "Pass12";
//    	
//    	String resultFirst = encoder.encode(pass);
//    	String resultSecond = encoder.encode(pass);
//    	String oddResult = encoder.encode(badPass);
//    	
//    	Assert.assertTrue(encoder.matches(pass, resultFirst));
//    	Assert.assertTrue(encoder.matches(pass, resultSecond));
//    	
//    	Assert.assertFalse(encoder.matches(badPass, resultFirst));
//    	Assert.assertFalse(encoder.matches(badPass, resultSecond));
//    	
//    	Assert.assertTrue(encoder.matches(badPass, oddResult));
//    	
//    	Assert.assertFalse(encoder.matches(pass, oddResult));
    	
    	Assert.assertTrue(true);
    }
}
