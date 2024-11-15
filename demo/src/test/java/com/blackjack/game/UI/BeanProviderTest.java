package com.blackjack.game.UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DirtiesContext
public class BeanProviderTest {

    @MockBean
    private ApplicationContext mockApplicationContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAutowireWithUninitializedContext() {
        // Ensure applicationContext is null
        assertThrows(IllegalStateException.class, () -> {
            BeanProvider.autowire(new Object());
        });
    }
}