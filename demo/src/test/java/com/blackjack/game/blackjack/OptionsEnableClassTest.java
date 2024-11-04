package com.blackjack.game.blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionsEnableClassTest {

    private OptionsEnableClass options;

    @BeforeEach
    public void setUp() {
        options = new OptionsEnableClass();
    }

    @Test
    public void testDefaultValues() {
        assertThat(options.getEnableHitButton()).isTrue();
        assertThat(options.getEnableStandButton()).isTrue();
        assertThat(options.getEnableInsuranceButton()).isFalse();
        assertThat(options.getEnableSplitButton()).isFalse();
        assertThat(options.getPlayerTurn()).isTrue();
    }

    @Test
    public void testSetEnableHitButton() {
        options.setEnableHitButton(false);
        assertThat(options.getEnableHitButton()).isFalse();

        options.setEnableHitButton(true);
        assertThat(options.getEnableHitButton()).isTrue();
    }

    @Test
    public void testSetEnableStandButton() {
        options.setEnableStandButton(false);
        assertThat(options.getEnableStandButton()).isFalse();

        options.setEnableStandButton(true);
        assertThat(options.getEnableStandButton()).isTrue();
    }

    @Test
    public void testSetEnableInsuranceButton() {
        options.setEnableInsuranceButton(true);
        assertThat(options.getEnableInsuranceButton()).isTrue();

        options.setEnableInsuranceButton(false);
        assertThat(options.getEnableInsuranceButton()).isFalse();
    }

    @Test
    public void testSetEnableSplitButton() {
        options.setEnableSplitButton(true);
        assertThat(options.getEnableSplitButton()).isTrue();

        options.setEnableSplitButton(false);
        assertThat(options.getEnableSplitButton()).isFalse();
    }

    @Test
    public void testSetPlayerTurn() {
        options.setPlayerTurn(false);
        assertThat(options.getPlayerTurn()).isFalse();

        options.setPlayerTurn(true);
        assertThat(options.getPlayerTurn()).isTrue();
    }
}