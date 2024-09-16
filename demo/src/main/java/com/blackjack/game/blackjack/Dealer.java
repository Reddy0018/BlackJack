package com.blackjack.game.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private List<CardObject> dealerCards = new ArrayList<>();
    private int total=0;
    Boolean winFlag = null;

    public List<CardObject> getDealerCards() {
        return dealerCards;
    }

    public void setDealerCards(List<CardObject> dealerCards) {
        this.dealerCards = dealerCards;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Boolean getWinFlag() {
        return winFlag;
    }

    public void setWinFlag(Boolean winFlag) {
        this.winFlag = winFlag;
    }
}
