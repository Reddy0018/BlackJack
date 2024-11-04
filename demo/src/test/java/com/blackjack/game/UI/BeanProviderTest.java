package com.blackjack.game.UI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        BeanProvider beanProvider = new BeanProvider();
        assertThrows(IllegalStateException.class, () -> {
            BeanProvider.autowire(new Object());
        });
    }
}