package com.blackjack.game.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String playerName;
    int bet=100;
    private List<CardObject> playerCards = new ArrayList<>();
    private int total=0;
    private Boolean winFlag = false;
    private  Boolean bustFlag =false;
    private OptionsEnableClass options = null;
    private Boolean blackjackWin = false;
    private Boolean gameDraw = false;

    public Boolean getGameDraw() {
        return gameDraw;
    }

    public void setGameDraw(Boolean gameDraw) {
        this.gameDraw = gameDraw;
    }

    public Boolean getBlackjackWin() {
        return blackjackWin;
    }

    public void setBlackjackWin(Boolean blackjackWin) {
        this.blackjackWin = blackjackWin;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public OptionsEnableClass getOptions() {
        return options;
    }

    public void setOptions(OptionsEnableClass options) {
        this.options = options;
    }

    public Boolean getBustFlag() {
        return bustFlag;
    }

    public void setBustFlag(Boolean bustFlag) {
        this.bustFlag = bustFlag;
    }

    public Player(String name){
        this.playerName = name;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<CardObject> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<CardObject> playerCards) {
        this.playerCards = playerCards;
    }

    public Boolean getWinFlag() {
        return winFlag;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerName='" + playerName + '\'' +
                ", playerCards=" + playerCards +
                ", total=" + total +
                ", winFlag=" + winFlag +
                '}';
    }

    public void setWinFlag(Boolean winFlag) {
        this.winFlag = winFlag;
    }
}
