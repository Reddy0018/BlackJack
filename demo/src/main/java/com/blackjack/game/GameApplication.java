package com.blackjack.game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Stack;

@SpringBootApplication
public class GameApplication {

	public static void main(String[] args) {
		System.out.println("Test");
		BlackJack blackJack = new BlackJack();
		SpringApplication.run(GameApplication.class, args);
	}


}
