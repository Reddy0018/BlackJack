package com.blackjack.game.user;

import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", "sxb1454@case.edu");
        loginDetails.put("password", "12345");

        when(userService.validateLoginDetails(anyMap())).thenReturn(true);
        Assertions.assertEquals(userController.login(loginDetails).get("status"),"Login Success");
    }

    @Test
    public void testLoginPasswordError() throws Exception {
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", "sxb1454@case.edu");
        loginDetails.put("password", "12345");

        when(userService.validateLoginDetails(anyMap())).thenThrow( new RuntimeException("Password didn't match!"));
        Assertions.assertEquals(userController.login(loginDetails).get("status"),"Password didn't match!");
    }

    @Test
    public void testLoginUserNotFound() throws Exception {
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", "sxb1454@case.edu");
        loginDetails.put("password", "12345");

        when(userService.validateLoginDetails(anyMap())).thenThrow( new RuntimeException("User Not Found!"));
        Assertions.assertEquals(userController.login(loginDetails).get("status"),"User Not Found!");
    }

    @Test
    void signUp() throws Exception {
        Map<String, String> signUpDetails = new HashMap<>();
        signUpDetails.put("email", "sxb1454@case.edu");
        signUpDetails.put("password", "12345");
        signUpDetails.put("firstName", "Sumanth Reddy");
        signUpDetails.put("lastName", "Bekkem");

        when(userService.validateSignUp(signUpDetails)).thenReturn(new User());
        Assertions.assertEquals(userController.signUp(signUpDetails).get("status"),"SignUp Successfully");

        when(userService.validateSignUp(signUpDetails)).thenThrow(new
                RuntimeException("User already Exists with email ID: "+ signUpDetails.get("email")));
        Assertions.assertEquals(userController.signUp(signUpDetails).get("status"),"User already Exists with email ID: "+ signUpDetails.get("email"));
    }

    @Test
    void deleteUser() throws Exception {
        Map<String, String> deleteDetails = new HashMap<>();
        deleteDetails.put("email", "sxb1454@case.edu");

        when(userService.deleteUser(deleteDetails)).thenReturn(new User());
        Assertions.assertEquals(userController.deleteUser(deleteDetails).get("status"),"Deleted Successfully");

        when(userService.deleteUser(deleteDetails)).thenThrow(new RuntimeException("User not found with email ID"));
        Assertions.assertEquals(userController.deleteUser(deleteDetails).get("status"),"User not found with email ID");

    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>(Arrays.asList(
                new User(1L,"Sumanth Reddy","Bekkem","sxb1454@case.edu","12345",0,0),
                new User(1L,"User1","1","1@case.edu","12345",0,0)));

        when(userService.getAllUsers()).thenReturn(users);
        Assertions.assertEquals(userController.getAllUsers(),users);
    }

    @Test
    void logout() {
        when(userService.getActiveLoggedInUser()).thenReturn(new User());
        Assertions.assertEquals(userController.logout(),"Logout Successful");
        Assertions.assertNull(UserService.getActiveUserName());
        when(userService.getActiveLoggedInUser()).thenReturn(null);
        Assertions.assertEquals(userController.logout(),"No Active LoggedIn User");
    }

    @Test
    void getLoggenInUser() {
        User user = new User(1L,"Sumanth Reddy","Bekkem",
                "sxb1454@case.edu","12345",0,0);
        when(userService.getActiveLoggedInUser()).thenReturn(user);
        Assertions.assertEquals(userController.getLoggenInUser(),user);
    }
}
