package com.blackjack.game;

import com.blackjack.game.UI.Login;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.*")
public class GameApplication {

	private static Login login = createLogin();

	public static void main(String[] args) {
		System.out.println("Test");
		login.createLoginScreen();
		SpringApplication.run(GameApplication.class, args);
	}

	protected static void setLogin(Login loginInstance) {
		login = loginInstance;
	}

	protected static Login createLogin() {
		return new Login();
	}
}
