package com.blackjack.game.blackjack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.blackjack.game.user.User;
import com.blackjack.game.user.UserRepository;
import com.blackjack.game.user.UserService;

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
        //userService=new UserService();
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
        //assertTrue(blackJack.dealerPlayFunction(player, dealer, deck));
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
        //assertFalse(dealer.getWinFlag());
        //assertTrue(blackJack.dealerPlayFunction(player, dealer, deck));
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

    // This method validates that a deck is correctly built with all necessary requirements
    void validateDeck(Stack<CardObject> deck) {
        
        assertNotNull(deck, "Deck should not be null");
        assertEquals(52, deck.size(), "Deck should contain 52 cards");

        // Check for duplicates by counting each card occurrence
        Map<String, Integer> cardOccurrences = new HashMap<>();
        for (CardObject card : deck) {
            String cardKey = card.getCardType() + "-" + card.getCardvalue();
            cardOccurrences.put(cardKey, cardOccurrences.getOrDefault(cardKey, 0) + 1);
        }

        // Ensure there are no duplicates
        assertTrue(cardOccurrences.values().stream().allMatch(count -> count == 1),
                "Deck should not contain duplicate cards");

        // Check suit and rank distribution
        Map<String, Integer> suitCount = new HashMap<>();
        Map<String, Integer> rankCount = new HashMap<>();
        for (CardObject card : deck) {
            suitCount.put(card.getCardType(), suitCount.getOrDefault(card.getCardType(), 0) + 1);
            rankCount.put(card.getCardvalue(), rankCount.getOrDefault(card.getCardvalue(), 0) + 1);
        }

        assertEquals(4, suitCount.size(), "Deck should have 4 suits");
        for (int count : suitCount.values()) {
            assertEquals(13, count, "Each suit should have 13 cards");
        }

        assertEquals(13, rankCount.size(), "Deck should have 13 unique ranks");
    }

    @Test
    void testBuildDeck() {

        BlackJack blackJack = new BlackJack();
        Stack<CardObject> deck = blackJack.buildDeck(new Stack<>());

        validateDeck(deck);
    }

    @Test
    void testInvalidDeckRebuild() {
        // Invalid deck with fewer than 52 cards
        Stack<CardObject> invalidDeck1 = new Stack<>();
        invalidDeck1.add(new CardObject("Hearts", "Ace"));
        invalidDeck1.add(new CardObject("Spades", "2"));

        assertTrue(invalidDeck1.size() < 52, "Invalid deck should contain fewer than 52 cards.");

        // Invalid deck with 52 cards but incorrect distribution
        Stack<CardObject> invalidDeck2 = new Stack<>();
        for (int i = 0; i < 13; i++) {
            invalidDeck2.add(new CardObject("Hearts", "Ace")); // Duplicate card
            invalidDeck2.add(new CardObject("Spades", "King"));
            invalidDeck2.add(new CardObject("Diamonds", "Queen"));
            invalidDeck2.add(new CardObject("Clubs", "Jack"));
        }

        assertEquals(52, invalidDeck2.size(), "Invalid deck has 52 cards, but incorrect distribution");

        // Check for duplicates in the invalid deck
        Map<String, Integer> invalidOccurrences = new HashMap<>();
        for (CardObject card : invalidDeck2) {
            String cardKey = card.getCardType() + "-" + card.getCardvalue();
            invalidOccurrences.put(cardKey, invalidOccurrences.getOrDefault(cardKey, 0) + 1);
        }

        boolean hasDuplicates = invalidOccurrences.values().stream().anyMatch(count -> count > 1);
        assertTrue(hasDuplicates, "Invalid deck should have duplicates or incorrect distribution");

        // Rebuild the deck after invalid scenarios
        BlackJack blackJack = new BlackJack();
        Stack<CardObject> rebuiltDeck = blackJack.buildDeck(new Stack<>());

        
        validateDeck(rebuiltDeck);
    }
    @Test
        void testCardShuffling() throws Exception {
        BlackJack blackJack = new BlackJack();
        Stack<CardObject> originalDeck = blackJack.buildDeck(new Stack<>());

        Stack<CardObject> copiedDeck = new Stack<>();
        copiedDeck.addAll(originalDeck);

        // Access and invoke the private shuffleDeck method using reflection
        java.lang.reflect.Method shuffleMethod = BlackJack.class.getDeclaredMethod("shuffleDeck", Stack.class);
        shuffleMethod.setAccessible(true); // Make the private method accessible
        shuffleMethod.invoke(blackJack, originalDeck);

        assertNotNull(originalDeck, "Deck should not be null after shuffling");

        // Validate that the size remains the same after shuffling
        assertEquals(copiedDeck.size(), originalDeck.size(), "Deck size should remain the same after shuffling");

        // Check that the order of cards has changed
        boolean isShuffled = false;
        for (int i = 0; i < originalDeck.size(); i++) {
            if (!originalDeck.get(i).equals(copiedDeck.get(i))) {
                isShuffled = true;
                break;
            }
        }

        assertTrue(isShuffled, "Deck should be shuffled, and the order of cards should be randomized");
    }
}
