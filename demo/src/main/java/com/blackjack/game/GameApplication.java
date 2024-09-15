package com.blackjack.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;



@SpringBootApplication
@ComponentScan("com.*")
public class GameApplication {

	public static void main(String[] args) {
		System.out.println("Test");
		BlackJack blackJack = new BlackJack();
		SpringApplication.run(GameApplication.class, args);
	}


}
