package com.blackjack.game.Interceptor;

import com.blackjack.game.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;

public class InterceptorTest {

    @InjectMocks
    private Interceptor interceptor;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;

    @Mock
    private ModelAndView modelAndView;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockStatic(UserService.class);
    }


    @Test
    public void testPreHandle() throws Exception {
        when(UserService.getActiveUserName()).thenReturn("testUser");

        // Call preHandle and assert the response
        assertTrue(interceptor.preHandle(request, response, handler));
        verify(response, never()).setStatus(HttpServletResponse.SC_FORBIDDEN);
        when(UserService.getActiveUserName()).thenReturn(null);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        // Call preHandle and assert the response
        assertFalse(interceptor.preHandle(request, response, handler));
        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);
        verify(response).setCharacterEncoding("UTF-8");
        verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
        printWriter.flush();
        assertTrue(stringWriter.toString().contains("User not authenticated needs to be loggedIn to perform operations!"));

        //testPostHandle Test Case
        interceptor.postHandle(request, response, handler, modelAndView);
        verify(response).setStatus(HttpServletResponse.SC_OK);

        //testAfterCompletion
        Exception ex = null;
        interceptor.afterCompletion(request, response, handler, ex);
    }

}