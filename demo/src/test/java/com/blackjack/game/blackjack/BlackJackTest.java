package com.blackjack.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.blackjack.game.UI.BeanProvider;
import com.blackjack.game.user.User;
import com.blackjack.game.user.UserRepository;
import com.blackjack.game.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class BlackJackTest {

    @Mock
    private UserRepository userRepository;

//    @MockBean
    private UserService userService;

    @InjectMocks
    private BlackJack blackJack;

    private Player player;
    private Player dealer;
    private Stack<CardObject> deck;
    User mockUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        userService=new UserService();
        player = new Player("Player");
        dealer = new Player("Dealer");
        deck = new Stack<>();
        deck.addAll(Arrays.asList(
                new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "A"),
                new CardObject("Clubs", "3"),
                new CardObject("Spades", "7"),
                new CardObject("Hearts", "5")
        ));
    }

    @Test
    public void testBlackJackInitialization() {
        List<Player> players = blackJack.BlackJack(player, dealer, deck);

        assertEquals(2, players.get(0).getPlayerCards().size());
        assertEquals(2, players.get(1).getPlayerCards().size());
        assertNotNull(players.get(0).getPlayerName());
        assertNotNull(players.get(1).getPlayerName());
    }

    /**@Test
    public void testDealerPlayFunction_PlayerWins() {
        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"), new CardObject("Diamonds", "A")));
        dealer.setPlayerCards(Arrays.asList(new CardObject("Clubs", "3"), new CardObject("Spades", "7")));
        blackJack.calculateCardsTotalValue(player);
        blackJack.calculateCardsTotalValue(dealer);

        when(userService.getActiveLoggedInUser()).thenReturn(new User());

        boolean result = blackJack.dealerPlayFunction(player, dealer, deck);

        assertTrue(result);
        assertTrue(player.getWinFlag());
        assertFalse(dealer.getWinFlag());
    }

    @Test
    public void testCalculateBlackJackStatus() {
        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"), new CardObject("Diamonds", "A")));
        blackJack.calculateCardsTotalValue(player);

        blackJack.calculateBlackJackStatus(player, dealer);

        assertTrue(player.getBlackjackWin());
        assertTrue(player.getWinFlag());
    }

    @Test
    public void testCalculatePlayerBustStatus() {
        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"), new CardObject("Diamonds", "8"), new CardObject("Clubs", "5")));
        blackJack.calculateCardsTotalValue(player);

        blackJack.calculatePlayerBustStatus(player, dealer);

        assertTrue(player.getBustFlag());
        assertFalse(player.getWinFlag());
    }*/

    @Test
    public void testCalculateCardsTotalValue() {
        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"), new CardObject("Diamonds", "A")));
        blackJack.calculateCardsTotalValue(player);

        assertEquals(21, player.getTotal());
    }
}