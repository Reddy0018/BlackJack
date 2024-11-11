package com.blackjack.game.blackjack;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

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

        /**assertThrows(RuntimeException.class,()->{
            gameController.playerHitStandButton();
        });*/

        playerList.get(0).setTotal(21);
        gameController.setOptions(playerList.get(0));
    }
}