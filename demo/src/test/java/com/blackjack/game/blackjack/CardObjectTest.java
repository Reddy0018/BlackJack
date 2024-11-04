package com.blackjack.game.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CardObjectTest {

    private CardObject card;

    @BeforeEach
    public void setUp() {
        card = new CardObject("Hearts", "Ace");
    }

    @Test
    public void testConstructor() {
        assertThat(card.getCardType()).isEqualTo("Hearts");
        assertThat(card.getCardvalue()).isEqualTo("Ace");
        assertThat(card.getHidden()).isFalse();
    }

    @Test
    public void testCardPath() {
        String expectedPath = "./cards/Hearts-Ace.png";
        assertThat(card.cardPath()).isEqualTo(expectedPath);
    }

    @Test
    public void testToString() {
        String expectedString = "Hearts-Ace";
        assertThat(card.toString()).isEqualTo(expectedString);
    }

    @Test
    public void testSetHidden() {
        card.setHidden(true);
        assertThat(card.getHidden()).isTrue();

        card.setHidden(false);
        assertThat(card.getHidden()).isFalse();
    }

    @Test
    public void testSetCardType() {
        card.setCardType("Spades");
        assertThat(card.getCardType()).isEqualTo("Spades");
    }

    @Test
    public void testSetCardvalue() {
        card.setCardvalue("King");
        assertThat(card.getCardvalue()).isEqualTo("King");
    }
}