package by.jpdm.test.mocks.view;

import by.jpdm.security.PasswordEncoder;
import by.jpdm.test.qualifiers.TestViewMock;

/**
 * Mock for manual view tests. Do not use for other purposes
 */
@TestViewMock
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
