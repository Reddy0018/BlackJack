package com.blackjack.game;

import com.blackjack.game.UI.Login;
import com.blackjack.game.blackjack.BlackJack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan("com.*")
public class GameApplication {

	private static final Login login = new Login();

	public static void main(String[] args) {
		System.out.println("Test");
		/**Test test = new Test();
		test.main(args);*/
		login.createLoginScreen();
		BlackJack blackJack = new BlackJack();
		SpringApplication.run(GameApplication.class, args);
	}


}
