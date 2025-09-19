import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginTest {

    private login login; // lowercase class name to match your code

    @BeforeEach
    void setUp() {
        login = new login();
    }

    @Test
    void checkUserName_valid() {
        assertTrue(login.checkUsername("usr_1"));
    }

    @Test
    void checkUserName_invalid() {
        assertFalse(login.checkUsername("username")); // no underscore, too long
    }

    @Test
    void checkPasswordComplexity_valid() {
        assertTrue(login.checkPasswordComplexity("Passw0rd!"));
    }

    @Test
    void checkPasswordComplexity_invalid() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void checkCellPhoneNumber_valid() {
        assertTrue(login.checkCellPhoneNumber("+27123456789"));
    }

    @Test
    void checkCellPhoneNumber_invalid() {
        assertFalse(login.checkCellPhoneNumber("0821234567"));
    }

    @Test
    void registerUser_invalidUsername_message() {
        String actual = login.registerUser("invalidUser", "Passw0rd!", "+27123456789", "John", "Doe");
        String expected = "Username is not correctly formatted. Please ensure your username contains an underscore and is no more than five characters in length.";
        assertEquals(expected, actual);
    }

    @Test
    void registerUser_invalidPassword_message() {
        String actual = login.registerUser("usr_1", "password", "+27123456789", "John", "Doe");
        String expected = "Password is not correctly formatted. Please ensure that the password has at least eight characters, a capital letter, a number, and a special character.";
        assertEquals(expected, actual);
    }

    @Test
    void registerUser_invalidCell_message() {
        String actual = login.registerUser("usr_1", "Passw0rd!", "0821234567", "John", "Doe");
        String expected = "Cellphone number is incorrectly formatted. It must start with +27 followed by 9 digits.";
        assertEquals(expected, actual);
    }

    @Test
    void registerUser_allValid_containsSuccessParts() {
        String actual = login.registerUser("usr_1", "Passw0rd!", "+27123456789", "John", "Doe");
        assertTrue(actual.contains("Username successfully captured."));
        assertTrue(actual.contains("Password successfully captured."));
        assertTrue(actual.contains("Cellphone successfully captured."));
        assertTrue(actual.contains("First name and Last name successfully captured."));
    }

    @Test
    void loginUser_success_and_statusMessage() {
        login.registerUser("usr_1", "Passw0rd!", "+27123456789", "John", "Doe");
        assertTrue(login.loginUser("usr_1", "Passw0rd!"));
        String expected = "Welcome John, Doe, it is great to see you again.";
        assertEquals(expected, login.returnLoginStatus());
    }

    @Test
    void loginUser_failure_and_statusMessage() {
        login.registerUser("usr_1", "Passw0rd!", "+27123456789", "John", "Doe");
        assertFalse(login.loginUser("usr_1", "wrongPass"));
        String expected = "Login failed. Please check your username and password.";
        assertEquals(expected, login.returnLoginStatus());
    }
}
