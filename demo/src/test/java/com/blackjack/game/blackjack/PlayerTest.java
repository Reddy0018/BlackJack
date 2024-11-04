package com.blackjack.game.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player("John Doe");
    }

    @Test
    public void testPlayerName() {
        assertThat(player.getPlayerName()).isEqualTo("John Doe");

        player.setPlayerName("Jane Doe");
        assertThat(player.getPlayerName()).isEqualTo("Jane Doe");
    }

    @Test
    public void testBet() {
        assertThat(player.getBet()).isEqualTo(100);

        player.setBet(200);
        assertThat(player.getBet()).isEqualTo(200);
    }

    @Test
    public void testPlayerCards() {
        List<CardObject> cards = new ArrayList<>();
        player.setPlayerCards(cards);
        assertThat(player.getPlayerCards()).isEqualTo(cards);
    }

    @Test
    public void testTotal() {
        assertThat(player.getTotal()).isEqualTo(0);

        player.setTotal(21);
        assertThat(player.getTotal()).isEqualTo(21);
    }

    @Test
    public void testWinFlag() {
        assertThat(player.getWinFlag()).isFalse();

        player.setWinFlag(true);
        assertThat(player.getWinFlag()).isTrue();
    }

    @Test
    public void testBustFlag() {
        assertThat(player.getBustFlag()).isFalse();

        player.setBustFlag(true);
        assertThat(player.getBustFlag()).isTrue();
    }

    @Test
    public void testOptions() {
        OptionsEnableClass options = new OptionsEnableClass();
        player.setOptions(options);
        assertThat(player.getOptions()).isEqualTo(options);
    }

    @Test
    public void testBlackjackWin() {
        assertThat(player.getBlackjackWin()).isFalse();

        player.setBlackjackWin(true);
        assertThat(player.getBlackjackWin()).isTrue();
    }

    @Test
    public void testGameDraw() {
        assertThat(player.getGameDraw()).isFalse();

        player.setGameDraw(true);
        assertThat(player.getGameDraw()).isTrue();
    }

    @Test
    public void testToString() {
        String expected = "Player{playerName='John Doe', playerCards=[], total=0, winFlag=false}";
        assertThat(player.toString()).isEqualTo(expected);
    }
}