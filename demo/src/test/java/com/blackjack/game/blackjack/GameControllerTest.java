package com.blackjack.game.blackjack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameControllerTest {

    @Autowired
    GameController gameController;

    @Test
    void testGameController() {
        List<Player> playerList = gameController.startGame();
        assertNotNull(playerList);
        assertEquals(playerList.get(0).getPlayerName(),"Sumanth Reddy");

        gameController.setPlayer(playerList.get(0));
        playerList = gameController.playerHitCards();
        assertNotNull(playerList);
        assertNotNull(playerList.get(0).getPlayerCards());

        playerList.get(0).getOptions().setPlayerTurn(false);
        assertThrows(RuntimeException.class,()->{
            gameController.playerHitCards();
        });

        playerList.get(0).setTotal(21);
        gameController.setOptions(playerList.get(0));
    }

    @Test
    void testPlayerHitCards() {
        List<Player> playerList = gameController.startGame();
        Player player = playerList.get(0);
        assertEquals(player.getPlayerName(),"Sumanth Reddy");
        Stack<CardObject> deck = gameController.getDeck();

        // THIS IS 48 BECAUSE THE GAME DRAWS TWO CARDS FOR THE PLAYER AND DEALER
        assertEquals(deck.size(), 48);

        // assert player's hand is 2 and ensure that hit card does not cause unknown behavior
        assertEquals(player.getPlayerCards().size(), 2);
        player.setPlayerCards(Arrays.asList(new CardObject("Spade", "2"), new CardObject("Clubs", "2")));

        gameController.playerHitCards();

        // assert player's hand has increase by one and deck's size has decrease by one
        assertEquals(player.getPlayerCards().size(), 3);
        assertEquals(deck.size(), 47);

        // assert drawn card is unique
        CardObject drawnCard = player.getPlayerCards().get(2);
        deck = gameController.getDeck();
        assertEquals(deck.search(drawnCard), -1);
    }
}