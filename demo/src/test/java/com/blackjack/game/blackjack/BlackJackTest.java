package com.blackjack.game.blackjack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.blackjack.game.user.User;
import com.blackjack.game.user.UserRepository;
import com.blackjack.game.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

@SpringBootTest
public class BlackJackTest {

    @Mock
    private UserRepository userRepository;

    @MockBean
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
        player.setOptions(new OptionsEnableClass());
        List<Player> players = blackJack.BlackJack(player, dealer, deck);

        assertEquals(2, players.get(0).getPlayerCards().size());
        assertEquals(2, players.get(1).getPlayerCards().size());
        assertNotNull(players.get(0).getPlayerName());
        assertNotNull(players.get(1).getPlayerName());
    }

    @Test
    public void testCalculateBlackJackStatus() {
        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "A")));
        player.setOptions(new OptionsEnableClass());
        blackJack.calculateCardsTotalValue(player);

        blackJack.calculateBlackJackStatus(player, dealer);

        assertTrue(player.getBlackjackWin());
        assertTrue(player.getWinFlag());
    }

    @Test
    public void testDealerPlayFunction_PlayerWins() {
        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "A")));
        player.setOptions(new OptionsEnableClass());
        dealer.setPlayerCards(Arrays.asList(new CardObject("Clubs", "3"),
                new CardObject("Spades", "7")));
        dealer.setOptions(new OptionsEnableClass());

        when(userService.getActiveLoggedInUser()).thenReturn(new User());
        blackJack.dealerPlayFunction(player, dealer, deck);

        player.setTotal(21);
        dealer.setTotal(18);
        when(userService.getActiveLoggedInUser()).thenReturn(new User());
        assertThrows(NullPointerException.class,()->{
            blackJack.dealerPlayFunction(player, dealer, deck);
        });

        dealer.setBlackjackWin(true);
        assertThrows(NullPointerException.class,()->{
            blackJack.dealerPlayFunction(player, dealer, deck);
        });

        player.setBustFlag(true);
        assertThrows(NullPointerException.class,()->{
            blackJack.dealerPlayFunction(player, dealer, deck);
        });

        player.setTotal(21);
        when(userService.getActiveLoggedInUser()).thenReturn(new User());
        assertThrows(NullPointerException.class,()->{
            blackJack.dealerPlayFunction(player, dealer, deck);
        });

        player.setTotal(18);
        dealer.setTotal(20);
        player.setBustFlag(false);
        dealer.setBlackjackWin(false);
        when(userService.getActiveLoggedInUser()).thenReturn(new User());
        assertThrows(NullPointerException.class,()->{
            blackJack.dealerPlayFunction(player, dealer, deck);
        });

        player.setTotal(21);
        dealer.setTotal(18);
        when(userService.getActiveLoggedInUser()).thenReturn(new User());
        assertThrows(NullPointerException.class,()->{
            blackJack.dealerPlayFunction(player, dealer, deck);
        });
    }

    @Test
    public void testCalculatePlayerBustStatus() {
        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "8"), new CardObject("Clubs", "5")));
        player.setOptions(new OptionsEnableClass());
        blackJack.calculateCardsTotalValue(player);

        blackJack.calculatePlayerBustStatus(player, dealer);

        assertTrue(player.getBustFlag());
        assertFalse(player.getWinFlag());
    }

    @Test
    public void testCalculateCardsTotalValue() {
        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"), new CardObject("Diamonds", "A")));
        blackJack.calculateCardsTotalValue(player);

        assertEquals(21, player.getTotal());
    }

    @Test
    public void testDealerPlayFunction_PlayerBust() {
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");
        Stack<CardObject> deck = new Stack<>();
        deck.addAll(Arrays.asList(
                new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "A"),
                new CardObject("Clubs", "3"),
                new CardObject("Spades", "7"),
                new CardObject("Hearts", "5")
        ));

        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "8"), new CardObject("Clubs", "5")));
        player.setOptions(new OptionsEnableClass());
        dealer.setPlayerCards(Arrays.asList(new CardObject("Clubs", "3"),
                new CardObject("Spades", "7")));
        dealer.setOptions(new OptionsEnableClass());

        blackJack.calculateCardsTotalValue(player);
        blackJack.calculateCardsTotalValue(dealer);

        blackJack.calculatePlayerBustStatus(player, dealer);

        assertTrue(player.getBustFlag());
        assertFalse(player.getWinFlag());
    }

    @Test
    public void testDealerPlayFunction_DealerBlackjack() {
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");
        Stack<CardObject> deck = new Stack<>();
        deck.addAll(Arrays.asList(
                new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "A"),
                new CardObject("Clubs", "3"),
                new CardObject("Spades", "7"),
                new CardObject("Hearts", "5")
        ));

        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "9")));
        player.setOptions(new OptionsEnableClass());
        dealer.setPlayerCards(Arrays.asList(new CardObject("Clubs", "A"),
                new CardObject("Spades", "10")));
        dealer.setOptions(new OptionsEnableClass());

        blackJack.calculateCardsTotalValue(player);
        blackJack.calculateCardsTotalValue(dealer);

        blackJack.calculateBlackJackStatus(dealer, player);

        assertTrue(dealer.getBlackjackWin());
    }

    @Test
    public void testDealerPlayFunction_DrawGame() {
        Player player = new Player("Player");
        Player dealer = new Player("Dealer");
        Stack<CardObject> deck = new Stack<>();
        deck.addAll(Arrays.asList(
                new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "A"),
                new CardObject("Clubs", "3"),
                new CardObject("Spades", "7"),
                new CardObject("Hearts", "5")
        ));

        player.setPlayerCards(Arrays.asList(new CardObject("Hearts", "10"),
                new CardObject("Diamonds", "8")));
        player.setOptions(new OptionsEnableClass());
        dealer.setPlayerCards(Arrays.asList(new CardObject("Clubs", "10"),
                new CardObject("Spades", "8")));
        dealer.setOptions(new OptionsEnableClass());

        blackJack.calculateCardsTotalValue(player);
        blackJack.calculateCardsTotalValue(dealer);

        assertFalse(player.getWinFlag());
        assertFalse(dealer.getWinFlag());
        assertTrue(blackJack.dealerPlayFunction(player, dealer, deck));
        assertTrue(player.getGameDraw());
        assertTrue(dealer.getGameDraw());
    }
}
