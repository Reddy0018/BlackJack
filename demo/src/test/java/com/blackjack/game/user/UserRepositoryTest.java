package com.blackjack.game.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserRepositoryTest {

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testFindByEmailNotFound() {
        User foundUser = userRepository.findByEmail(anyString());
        Assertions.assertNull(foundUser);
    }
}