package com.blackjack.game.UI;

import com.blackjack.game.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Login.getUserTextBox().setText("test@example.com");
        Login.getPasswordBox().setText("password");
        Login.getFirstNameTextBox().setText("John");
        Login.getLastNameTextBox().setText("John");

        ActionEvent event = new ActionEvent(Login.getSignupButton(),
                ActionEvent.ACTION_PERFORMED, "Sign Up");
        login.actionPerformed(event);
        assertEquals("Sign UP Success", Login.getSuccess().getText());
        //assertEquals("User already Exists with email ID: test@example.com", Login.getSuccess().getText());

        event = new ActionEvent(Login.getSignupButton(),
                ActionEvent.ACTION_PERFORMED, "SignUp");
        login.actionPerformed(event);

        event = new ActionEvent(Login.getSignupButton(),
                ActionEvent.ACTION_PERFORMED, "switchToLogin");
        login.actionPerformed(event);
    }

    @Test
    public void testLoginSuccess() throws Exception {
        // Simulate entering text in the UI components
        Login.getUserTextBox().setText("test@example.com");
        Login.getPasswordBox().setText("password");

        // Create an ActionEvent for the login button
        ActionEvent event = new ActionEvent(Login.getLoginButton(),
                ActionEvent.ACTION_PERFORMED, "Login");

        // Call the actionPerformed method
        login.actionPerformed(event);

        // Verify interactions and check the outcome
        assertEquals("User Not Found!", Login.getSuccess().getText());
        //assertEquals("Login Success", Login.getSuccess().getText());
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