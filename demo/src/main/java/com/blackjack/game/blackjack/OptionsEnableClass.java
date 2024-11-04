package com.blackjack.game.blackjack;


public class OptionsEnableClass {
    private Boolean enableHitButton =true;
    private Boolean enableStandButton = true;
    private Boolean enableInsuranceButton = false;
    private Boolean enableSplitButton = false;
    private Boolean playerTurn = true;

    public Boolean getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Boolean getEnableHitButton() {
        return enableHitButton;
    }

    public void setEnableHitButton(Boolean enableHitButton) {
        this.enableHitButton = enableHitButton;
    }

    public Boolean getEnableStandButton() {
        return enableStandButton;
    }

    public void setEnableStandButton(Boolean enableStandButton) {
        this.enableStandButton = enableStandButton;
    }

    public Boolean getEnableInsuranceButton() {
        return enableInsuranceButton;
    }

    public void setEnableInsuranceButton(Boolean enableInsuranceButton) {
        this.enableInsuranceButton = enableInsuranceButton;
    }

    public Boolean getEnableSplitButton() {
        return enableSplitButton;
    }

    public void setEnableSplitButton(Boolean enableSplitButton) {
        this.enableSplitButton = enableSplitButton;
    }
}
