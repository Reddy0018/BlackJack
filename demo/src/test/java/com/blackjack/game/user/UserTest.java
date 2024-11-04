package com.blackjack.game.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setTotalWins(10);
        user.setTotalLosses(5);
    }

    @Test
    public void testGettersAndSetters() {
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getTotalWins()).isEqualTo(10);
        assertThat(user.getTotalLosses()).isEqualTo(5);
    }

    @Test
    public void testToString() {
        String expectedToString = "User{id=1, firstName='John', lastName='Doe', email='john.doe@example.com', password='password'}";
        assertThat(user.toString()).isEqualTo(expectedToString);
    }

    @Test
    public void testNoArgsConstructor() {
        User newUser = new User();
        assertThat(newUser.getId()).isNull();
        assertThat(newUser.getFirstName()).isNull();
        assertThat(newUser.getLastName()).isNull();
        assertThat(newUser.getEmail()).isNull();
        assertThat(newUser.getPassword()).isNull();
        assertThat(newUser.getTotalWins()).isNull();
        assertThat(newUser.getTotalLosses()).isNull();
    }

    @Test
    public void testAllArgsConstructor() {
        User newUser = new User(2L, "Jane", "Doe", "jane.doe@example.com", "securepassword", 15, 3);
        assertThat(newUser.getId()).isEqualTo(2L);
        assertThat(newUser.getFirstName()).isEqualTo("Jane");
        assertThat(newUser.getLastName()).isEqualTo("Doe");
        assertThat(newUser.getEmail()).isEqualTo("jane.doe@example.com");
        assertThat(newUser.getPassword()).isEqualTo("securepassword");
        assertThat(newUser.getTotalWins()).isEqualTo(15);
        assertThat(newUser.getTotalLosses()).isEqualTo(3);
    }
}