package com.blackjack.game.Interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppConfigTest {

    @InjectMocks
    private AppConfig appConfig;

    @Mock
    private Interceptor interceptor;

    @Mock
    private InterceptorRegistry registry;

    @Mock
    private InterceptorRegistration registration;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddInterceptors() {
        // Mock the behavior of addInterceptor to return a mock InterceptorRegistration
        when(registry.addInterceptor(interceptor)).thenReturn(registration);

        // Mock method chaining on InterceptorRegistration
        when(registration.addPathPatterns(any(String[].class))).thenReturn(registration);
        when(registration.excludePathPatterns(any(String[].class))).thenReturn(registration);

        // Call the method to test
        appConfig.addInterceptors(registry);

        // Verify that addInterceptor was called on the registry
        verify(registry).addInterceptor(interceptor);

        // Verify that the interceptor is registered with the correct path patterns and exclusions
        verify(registration).addPathPatterns("/api/v1/**", "/loginApi/v1/**");
        verify(registration).excludePathPatterns("/**/login", "/**/SignUp", "/**/logout");
    }
}