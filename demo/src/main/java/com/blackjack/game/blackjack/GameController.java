package com.blackjack.game.blackjack;

import com.blackjack.game.user.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(path = "api/v1")
public class GameController {

    private BlackJack blackJack = null;
    @Getter
    private Stack<CardObject> deck;
    @Setter
    private Player player = null;
    private OptionsEnableClass playerOptions = null;
    private Player dealer = null;
    private OptionsEnableClass dealerOptions = null;


    @GetMapping(path = "/startGame")
    public List<Player> startGame(){
        blackJack= new BlackJack();
        /**if(null==UserService.getActiveUserName()){
            throw new RuntimeException("Need To Login to play Blackjack");
        }*/
        player = new Player(UserService.getActiveUserName()!=null ? UserService.getActiveUserName() : "Sumanth Reddy" );
        playerOptions = new OptionsEnableClass();

        dealer = new Player("Dealer");
        dealerOptions = new OptionsEnableClass();

        player.setOptions(playerOptions);
        dealer.setOptions(dealerOptions);
        deck = blackJack.buildDeck(deck); /** Building Deck*/
        return blackJack.BlackJack(player,dealer,deck);
    }

    /**@GetMapping(path = "/test")
    public Player testMethod(){
        return  player;
    }*/

    @GetMapping(path = "/PlayerHitCards")
    public List<Player> playerHitCards(){
        /**
         * Flow of this method
         * pop a card from available deck
         * Calculate the Total value of the players available cards
         * Set Bust flag as true if value >21
         * Set Win flag = true if score == 21
         * repeat the fow until the player wants to stand without busting
         */
        if(!player.getOptions().getPlayerTurn()){
            throw new RuntimeException("Player Turn Expired!");
        }

        List<CardObject> playerCards = new ArrayList<>(player.getPlayerCards());
        playerCards.add(deck.pop());
        player.setPlayerCards(playerCards);
        blackJack.calculateCardsTotalValue(player);
        blackJack.calculatePlayerBustStatus(player, dealer);
        setOptions(player);

        return Arrays.asList(player,dealer);
    }

    public void setOptions(Player player){
        if(player.getTotal()==21){
            player.getOptions().setEnableHitButton(false);
            player.getOptions().setEnableStandButton(false);
            player.setWinFlag(true);
            dealer.setBustFlag(true);
        }
    }

    @GetMapping(path = "/playerHitStand")
    public List<Player> playerHitStandButton(){
        /**
         * Flow of this method is to do the following:
         * Disable Player buttons (Hit & Stand)
         * Transfer the Play to Dealer
         * Dealer play should be automated
         *  Dealer play flow:
         *  Needs to calculate dealer play cards value
         *  if(total>17):
         *  dealer can't take anymore cards from dec pile
         *  and if dealer total and player total needs to be compared for the winner
         *  else:
         *  dealer can draw cards from the dec pile until the total is less than 17
         */
        player.getOptions().setPlayerTurn(false);
        player.getOptions().setEnableHitButton(false);

        blackJack.dealerPlayFunction(player,dealer,deck);

        return Arrays.asList(player,dealer);
    }

}
