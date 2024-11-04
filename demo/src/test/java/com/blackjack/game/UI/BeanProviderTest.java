package com.blackjack.game.UI;

import static org.junit.jupiter.api.Assertions.*;

import com.blackjack.game.UI.BeanProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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