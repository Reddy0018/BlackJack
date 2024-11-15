package com.blackjack.game.blackjack;

public class CardObject {
    private String cardType;
    private String cardvalue;
    private Boolean hidden=false;

    public Boolean getHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public  CardObject(String cardType, String cardvalue){
        this.cardType=cardType;
        this.cardvalue=cardvalue;
    }

    public String cardPath(){
        return "./cards/"+this.cardType+"-"+this.cardvalue+".png";
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardvalue() {
        return cardvalue;
    }

    public void setCardvalue(String cardvalue) {
        this.cardvalue = cardvalue;
    }

    @Override
    public String toString() {
        return cardType+"-"+cardvalue;
    }

    public boolean equals(CardObject a) {
        if(a.getCardType().equals(this.getCardType())) {
            if(a.getCardvalue().equals(this.getCardvalue())) {
                return true;
            }
        }
        return false;
    }
}
