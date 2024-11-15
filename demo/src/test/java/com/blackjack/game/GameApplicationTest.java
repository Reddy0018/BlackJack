package com.blackjack.game;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.blackjack.game.UI.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GameApplicationTest {

	private Login loginMock;

	@BeforeEach
	public void setUp() {
		loginMock = mock(Login.class);
		GameApplication.setLogin(loginMock);
	}

	@Test
	public void testMain() {
		String[] args = {};

		try (MockedStatic<SpringApplication> springApplicationMock = Mockito.mockStatic(SpringApplication.class)) {
			springApplicationMock.when(() -> SpringApplication.run(GameApplication.class, args)).thenReturn(null);

			// Call the main method
			GameApplication.main(args);

			// Verify that createLoginScreen is called
			verify(loginMock).createLoginScreen();

			// Verify that SpringApplication.run is called
			springApplicationMock.verify(() -> SpringApplication.run(GameApplication.class, args));
		}
	}
}
