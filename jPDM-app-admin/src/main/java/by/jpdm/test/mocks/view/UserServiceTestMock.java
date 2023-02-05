package by.jpdm.test.mocks.view;

import java.security.SecureRandom;
import java.util.Random;

import javax.inject.Inject;

import by.jpdm.model.beans.org.User;
import by.jpdm.model.service.UserService;
import by.jpdm.security.PasswordEncoder;
import by.jpdm.test.qualifiers.TestViewMock;

/**
 * Mock for manual view tests. Do not use for other purposes
 */
@TestViewMock
public class UserServiceTestMock implements UserService {
    private static final int SALT_LENGTH = 16;
    final Random random = new SecureRandom();
    
    @Inject @TestViewMock
    private PasswordEncoder passwordEncoder;

    public User createUser(String login, String name, String password) {
        User user = new User(login, name);
        String salt = generateStaticSymbolicSalt();
        user.setSalt(salt);
        String encodedPassword = encodePassword(password, salt);
        user.setPassword(encodedPassword);
        return user;
    }

    public String encodePassword(String password, String salt) {
        return passwordEncoder.encode(password + salt);
    }

    private String generateStaticSymbolicSalt() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        while (counter < SALT_LENGTH) {
            int val = random.nextInt(123);
            if (isGoodChar(val)) {
                counter++;
                sb.append(((char) val));
            }
        }
        return sb.toString();
    }

    private boolean isGoodChar(int ch) {
        return ((ch < 123) && (ch > 96)) || ((ch < 91) && (ch > 64)) || ((ch < 58) && (ch > 47));
    }
}
