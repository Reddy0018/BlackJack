package com.blackjack.game.blackjack;

import com.blackjack.game.UI.BeanProvider;
import com.blackjack.game.user.User;
import com.blackjack.game.user.UserRepository;
import com.blackjack.game.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class BlackJack {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public List<Player> BlackJack(Player player, Player dealer, Stack<CardObject> deck) {
        //Shuffling the building deck
        shuffleDeck(deck);

        //need to implement initial card distribution func to players
        player.setPlayerCards(Arrays.asList(deck.pop(),deck.pop()));
        dealer.setPlayerCards(Arrays.asList(deck.pop(),deck.pop()));
        dealer.getPlayerCards().get(1).setHidden(true);


        //Calculating initial total Score of both players
        calculateCardsTotalValue(player);
        calculateCardsTotalValue(dealer);

        //Calculate if the player won BlackJack
        calculateBlackJackStatus(player,dealer);
        //Calculate if the player Score went above 21
        calculatePlayerBustStatus(player,dealer);

        return Arrays.asList(player,dealer);
    }

    public Stack<CardObject>  buildDeck(Stack<CardObject> deck){
        deck = new Stack<>();
        List<String> cardTypes = Arrays.asList("Spade","Hearts","Diamonds","Clubs");
        List<String> cardValues = Arrays.asList("2","3","4","5","6","7","8","9","10","A","Jack","Queen","King");
        CardObject object = null;
        for(String cardType: cardTypes){
            for(String cardValue: cardValues){
                object = new CardObject(cardType,cardValue);
                deck.add(object);
            }
        }
        return deck;
    }

    private void updateUserWinORLoss(String action){
        BeanProvider.autowire(this);
        User user = userService.getActiveLoggedInUser();
        if(action.equals("Loss")){
            user.setTotalLosses(user.getTotalLosses()+1);
            userRepository.save(user);
            return;
        }
        user.setTotalWins(user.getTotalWins()+1);
        userRepository.save(user);
    }

    public boolean dealerPlayFunction(Player player, Player dealer, Stack<CardObject> deck){

        dealer.getPlayerCards().get(1).setHidden(false);
        if(player.getBustFlag()){
            updateUserWinORLoss("Loss");
            return true;
        }
        calculateBlackJackStatus(dealer,player);
        if(dealer.getBlackjackWin()){
            updateUserWinORLoss("Loss");
            return true;
        }
        if(player.getTotal()==21){
            player.setWinFlag(true);
            dealer.setWinFlag(false);
            dealer.setBustFlag(true);
            updateUserWinORLoss("W");
            return  true;
        }else if (dealer.getTotal()>player.getTotal()){
            player.setWinFlag(false);
            dealer.setWinFlag(true);
            player.setBustFlag(true);
            dealer.setBustFlag(false);
            updateUserWinORLoss("Loss");
            return true;
        }

        if(dealer.getTotal()==player.getTotal()){
            dealer.setGameDraw(true);
            player.setGameDraw(true);
            return true;
        }

        if(dealer.getTotal()>17 && dealer.getTotal()<player.getTotal()){
                dealer.setBustFlag(true);
                dealer.setWinFlag(false);
                player.setWinFlag(true);
                updateUserWinORLoss("W");
                return true;
        }

        while (dealer.getTotal()<=17){
            List<CardObject> dealerCards = new ArrayList<>(dealer.getPlayerCards());
            dealerCards.add(deck.pop());
            dealer.setPlayerCards(dealerCards);
            calculateCardsTotalValue(dealer);
            if(dealer.getTotal()>21){
                calculatePlayerBustStatus(dealer,player);
                updateUserWinORLoss("W");
                break;
            }else if (dealer.getTotal()>player.getTotal()){
                dealer.setWinFlag(true);
                player.setBustFlag(true);
                player.setWinFlag(false);
                updateUserWinORLoss("Loss");
                break;
            }else if (dealer.getTotal() == player.getTotal()){
                dealer.setGameDraw(true);
                player.setGameDraw(true);
                break;
            }
        }
        return true;
    }

    private void shuffleDeck(Stack<CardObject> deck){
        Collections.shuffle(deck);
    }

    public void calculateBlackJackStatus(Player player, Player dealer){
        if(player.getTotal()==21){
            player.getOptions().setEnableHitButton(false);
            player.getOptions().setEnableStandButton(false);
            player.setWinFlag(true);
            player.setBlackjackWin(true);
        }
    }

    public void calculatePlayerBustStatus(Player player, Player dealer){
        if(player.getTotal()>21){
            player.getOptions().setEnableHitButton(false);
            player.getOptions().setEnableStandButton(false);
            player.setBustFlag(true);
            player.setWinFlag(false);
            dealer.setWinFlag(true);
            player.getOptions().setEnableHitButton(false);
        }
    }

    public void calculateCardsTotalValue(Player player){
        int total = 0;
        int totalAces =0;
        for(CardObject cardObject: player.getPlayerCards()){
            if(cardObject.getCardvalue().contains("Queen")||
                    cardObject.getCardvalue().contains("Jack")||
                    cardObject.getCardvalue().contains("King")){
                total = total + 10;
            } else if (cardObject.getCardvalue().contains("A")) {
                totalAces+=1;
            } else {
                total = total + Integer.parseInt(cardObject.getCardvalue());
            }
        }
        if(totalAces!=0){
            for(int i=0; i<totalAces; i++){
                if(total+11<=21){
                    total+=11;
                }else {
                    total+=1;
                }
            }
        }
        player.setTotal(total);
    }

}
