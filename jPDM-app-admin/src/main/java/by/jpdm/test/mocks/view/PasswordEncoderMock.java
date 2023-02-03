package by.jpdm.test.mocks.view;

import by.jpdm.security.PasswordEncoder;

/**
 * Mock for manual view tests. Do not use for other purposes
 */
public class PasswordEncoderMock implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }
}
