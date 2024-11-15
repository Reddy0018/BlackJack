package com.blackjack.game.UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DirtiesContext
public class BeanProviderTest {

    @Mock
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