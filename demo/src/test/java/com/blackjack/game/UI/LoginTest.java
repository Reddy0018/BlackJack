package com.blackjack.game.UI;

import com.blackjack.game.user.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoginTest {

    @InjectMocks
    private Login login;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        System.setProperty("java.awt.headless", "false");
        login.createLoginScreen();
        login.createSignupScreen();
    }

    @Test
    public void testSignUpSuccess() throws Exception {
        Map<String, String> deleteDetails = new HashMap<>();
        deleteDetails.put("email", "test@example.com");
        userService.deleteUser(deleteDetails);

        Login.getUserTextBox().setText("test@example.com");
        Login.getPasswordBox().setText("password");
        Login.getFirstNameTextBox().setText("John");
        Login.getLastNameTextBox().setText("John");

        ActionEvent event = new ActionEvent(Login.getSignupButton(), ActionEvent.ACTION_PERFORMED, "Sign Up");
        login.actionPerformed(event);
        assertEquals("Sign UP Success", Login.getSuccess().getText());

        event = new ActionEvent(Login.getSignupButton(), ActionEvent.ACTION_PERFORMED, "SignUp");
        login.actionPerformed(event);

        event = new ActionEvent(Login.getSignupButton(), ActionEvent.ACTION_PERFORMED, "switchToLogin");
        login.actionPerformed(event);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        Login.getUserTextBox().setText("test@example.com");
        Login.getPasswordBox().setText("password");
        ActionEvent event = new ActionEvent(Login.getLoginButton(), ActionEvent.ACTION_PERFORMED, "Login");
        login.actionPerformed(event);
        assertEquals("Login Success", Login.getSuccess().getText());
        assertEquals(true, Login.getStartGame().isEnabled());
    }

    @Test
    public void testLoginNoUser() {
        Login.getUserTextBox().setText("FAILURE!!!");
        Login.getPasswordBox().setText("password");
        ActionEvent event = new ActionEvent(Login.getLoginButton(), ActionEvent.ACTION_PERFORMED, "Login");
        login.actionPerformed(event);
        assertEquals("User Not Found!", Login.getSuccess().getText());
        assertEquals(false, Login.getStartGame().isEnabled());
    }

    @Test
    public void testLoginNoPassword() {
        Login.getUserTextBox().setText("test@example.com");
        Login.getPasswordBox().setText("WRONGPASSWORD");
        ActionEvent event = new ActionEvent(Login.getLoginButton(), ActionEvent.ACTION_PERFORMED, "Login");
        login.actionPerformed(event);
        assertEquals("Password didn't match!", Login.getSuccess().getText());
        assertEquals(false, Login.getStartGame().isEnabled());
    }

    @Test
    public void testEmptyFields() {
        // Ensure fields are empty
        Login.getUserTextBox().setText("");
        Login.getPasswordBox().setText("");

        // Create an ActionEvent for the login button
        ActionEvent event = new ActionEvent(Login.getLoginButton(), ActionEvent.ACTION_PERFORMED, "Login");

        // Call the actionPerformed method
        login.actionPerformed(event);

        // Check the outcome
        assertEquals("Fields cannot be empty!", Login.getSuccess().getText());
    }
}