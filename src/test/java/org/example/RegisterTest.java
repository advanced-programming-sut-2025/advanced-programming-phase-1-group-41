package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RegisterTest {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{1,8}$";
    private static final String PASSWORD_REGEX = "^.{8,}$";
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";


    @Test
    void testValidUsername() {
        String username = "User123";
        assertTrue(username.matches(USERNAME_REGEX));
    }

    @Test
    void testInvalidUsernameLength() {
        String username = "TooLongUser";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
//            username.matches(USERNAME_REGEX);
                validateUsername("TooLongUser");
        });
        assertEquals("Username must not exceed 8 characters", exception.getMessage());

    }

    @Test
    void testValidPassword() {
        String password = "SecurePass";
        assertTrue(password.matches(PASSWORD_REGEX));
    }

    @Test
    void testInvalidPassword() {
        String password = "short";
        assertFalse(password.matches(PASSWORD_REGEX));
    }

    @Test
    void testValidEmail() {
        String email = "test@example.com";
        assertTrue(email.matches(EMAIL_REGEX));
    }

    @Test
    void testInvalidEmail() {
        String email = "invalidemail.com";
        assertFalse(email.matches(EMAIL_REGEX));
    }
    void validateUsername(String username) {
        if (username.length() > 8) {
            throw new IllegalArgumentException("Username must not exceed 8 characters");
        }
    }
}