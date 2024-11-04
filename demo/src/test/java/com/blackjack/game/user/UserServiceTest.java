package com.blackjack.game.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private Encryption encryption;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setEmail("sxb1454@case.edu");
        //user.setPassword("12345");
        user.setPassword(encryption.encrypt("password123"));
        user.setFirstName("Sumanth Reddy");
        user.setLastName("Bekkem");
    }

    @Test
    void validateLoginDetails() throws Exception {
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("email", "sxb1454@case.edu");
        loginDetails.put("password", "12345");//CYYW/vhCK3gjS1ehUkeY4A==

        /** Success Scenario*/
        when(userRepository.findByEmail("sxb1454@case.edu")).thenReturn(user);
        when(encryption.decrypt(user.getPassword())).thenReturn("12345");
        boolean result = userService.validateLoginDetails(loginDetails);
        assertTrue(result);
        assertEquals("Sumanth Reddy,Bekkem", UserService.getActiveUserName());

        /** Wrong Password Scenario */
        user.setPassword("1");
        user.setPassword(encryption.encrypt("wrongpassword"));
        when(encryption.decrypt(user.getPassword())).thenReturn("wrongpassword");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.validateLoginDetails(loginDetails));
        assertEquals("Password didn't match!", exception.getMessage());

        /** Null User Scenario */
        when(userRepository.findByEmail("sxb1454@case.edu")).thenReturn(null);
        exception = assertThrows(RuntimeException.class, () -> userService.validateLoginDetails(loginDetails));
        assertEquals("User Not Found!", exception.getMessage());
    }

    @Test
    public void validateSignUp() throws Exception {
        Map<String, String> signUpDetails = new HashMap<>();
        signUpDetails.put("email", "sxb1454@case.edu");
        signUpDetails.put("firstName", "Sumanth Reddy");
        signUpDetails.put("lastName", "Bekkem");
        signUpDetails.put("password", "password");

        /** Success Scenario */
        when(userRepository.findByEmail("newuser@example.com")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(encryption.encrypt(anyString())).thenReturn("password");
        User result = userService.validateSignUp(signUpDetails);

        assertNotNull(result);
        assertEquals("sxb1454@case.edu", result.getEmail());
        assertEquals("Sumanth Reddy", result.getFirstName());
        assertEquals("Bekkem", result.getLastName());
        assertEquals("password", result.getPassword());
        assertEquals(0, result.getTotalWins());
        assertEquals(0, result.getTotalLosses());

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        /** User already Exists Scenario */
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.validateSignUp(signUpDetails);
        });
        assertEquals("User already Exists with email ID: sxb1454@case.edu", exception.getMessage());
    }

    @Test
    public void testDeleteUserSuccess() {
        Map<String, String> deleteUserDetails = new HashMap<>();
        deleteUserDetails.put("email", "sxb1454@case.edu");

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        User result = userService.deleteUser(deleteUserDetails);

        verify(userRepository, times(1)).deleteById(user.getId());
        assertNotNull(result);
        assertEquals("sxb1454@case.edu", result.getEmail());
    }

    @Test
    public void testDeleteUserNotFound() {
        Map<String, String> deleteUserDetails = new HashMap<>();
        deleteUserDetails.put("email", "sxb1454@case.edu");

        when(userRepository.findByEmail(anyString())).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(deleteUserDetails);
        });

        assertEquals("User not found with email ID: sxb1454@case.edu", exception.getMessage());
    }

    @Test
    void getAllUsers() {
        List<User> users = new ArrayList<>(Arrays.asList(
                user,
                new User(1L,"User1","1","1@case.edu","12345",0,0)));

        when(userService.getAllUsers()).thenReturn(users);
        Assertions.assertEquals(userService.getAllUsers(),users);
    }

    @Test
    void getActiveLoggedInUser() {
        userService.setUser(user);
        User activeUser = userService.getActiveLoggedInUser();
        assertNotNull(activeUser);
        assertEquals("sxb1454@case.edu", activeUser.getEmail());
    }
}