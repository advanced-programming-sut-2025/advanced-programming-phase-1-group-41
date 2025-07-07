package com.CEliconValley;

import static org.junit.jupiter.api.Assertions.*;

import com.CEliconValley.controllers.authentication.AuthenticationController;
import com.CEliconValley.models.Result;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class RegisterTest {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{1,8}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~]+$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9][a-zA-Z0-9._-]*@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$";

    AuthenticationController authenticationController = new AuthenticationController();
    Result result;

    @Test
    void testValidUsername() {
        String username = "register -u User123";
        assertTrue(username.matches(USERNAME_REGEX));
    }

    @Test
    void testInvalidUsernameFormatError() {
//        String register = "register -u LongUsername -p Test@123 Test@123 -e email@gmail.com -g male";
//        assertEquals(username.matches(USERNAME_REGEX), "Username must not exceed 8 characters.");
    }

    @Test
    void testValidEmail() {
        String email = "test@example.com";
        assertTrue(email.matches(EMAIL_REGEX));
    }

    @Test
    void testInvalidEmail1() {
        String email = "invalidemail.com";
        assertTrue(email.matches(EMAIL_REGEX), "Email must have '@' between.");
    }

    @Test
    void testInvalidEmail2() {
        String email = "inv..alid@email.com";
        assertTrue(email.matches(EMAIL_REGEX), "Email cn have at last 1 '.' in Domain or Name");
    }

    @Test
    void testInvalidEmail3() {
        String email = "-invalid@-email.com";
        assertTrue(email.matches(EMAIL_REGEX), "Domain and Name part should start with letter or number.");
    }

    @Test
    void testInvalidEmail4() {
        String email = "invalid@email";
        assertTrue(email.matches(EMAIL_REGEX), "Domain should have suffix.");
    }

    @Test
    void testInvalidEmail5() {
        String email = "invalid@email.i";
        assertTrue(email.matches(EMAIL_REGEX), "Domain suffix should have at least two letters.");
    }

    @Test
    void testValidPassword() {
        String password = "SecurePass1@";
        assertTrue(password.matches(PASSWORD_REGEX));
    }

    @Test
    void testInvalidPassword1() {
        String password = "Short1@";
        assertFalse(password.matches(PASSWORD_REGEX), "Password must be at least 8 characters!");
    }

    @Test
    void testInvalidPassword2() {
        String password = "UPPERCASE1@";
        assertFalse(password.matches(PASSWORD_REGEX), "Password must contain at least one lowercase letter!");
    }


    @Test
    void testInvalidPassword3() {
        String password = "lowercase1@";
        assertFalse(password.matches(PASSWORD_REGEX), "Password must contain at least one uppercasse letter!");
    }

    @Test
    void testInvalidPassword4() {
        String password = "NoDigitPass@";
        assertFalse(password.matches(PASSWORD_REGEX), "Password must contain at least one digit!");
    }

    @Test
    void testInvalidPassword5() {
        String password = "NoSpecialPass1";
        assertFalse(password.matches(PASSWORD_REGEX), "Password must contain at least one special character!");
    }

    @Test
    void testUnequalPasswords(){
        String password = "CorrectPass1@";
        String password2 = "CorrectPass1@";
        assertEquals(password, password2);
    }

    @Test
    void testEqualPasswords(){
        String password = "CorrectPass1@";
        String password2 = "UnequalPass1@";
        assertEquals(password, password2, "Passwords do not match!");
    }

    @Before
    public void setUp() {
        System.out.println("Setting up ...");
    }

    void validateUsername(String username) {
        if (username.length() > 8) {
            System.out.println("Error: Username must not exceed 8 characters");
            throw new IllegalArgumentException("Username must not exceed 8 characters");
        }
    }
}