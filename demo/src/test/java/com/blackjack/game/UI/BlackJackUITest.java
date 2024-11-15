package com.blackjack.game.UI;

import com.blackjack.game.blackjack.CardObject;
import com.blackjack.game.user.User;
import com.blackjack.game.user.UserController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class BlackJackUITest {

    BlackJackUI blackJackUI = new BlackJackUI();

    @Mock
    Graphics graphics;

    @MockBean
    UserController userController;

    @Test
    void buildBlackJackUI() {
        when(userController.getLoggenInUser()).thenReturn(new User());
        blackJackUI.buildBlackJackUI();
        JButton jButton = new JButton();
        ActionEvent event = new ActionEvent(jButton, ActionEvent.ACTION_PERFORMED, "startOver");
        blackJackUI.actionPerformed(event);
        event = new ActionEvent(jButton, ActionEvent.ACTION_PERFORMED, "close");
        blackJackUI.actionPerformed(event);
        event = new ActionEvent(jButton, ActionEvent.ACTION_PERFORMED, "default");
        blackJackUI.actionPerformed(event);
        event = new ActionEvent(jButton, ActionEvent.ACTION_PERFORMED, "Hit");
        ActionEvent finalEvent = event;
        blackJackUI.actionPerformed(finalEvent);
        event = new ActionEvent(jButton, ActionEvent.ACTION_PERFORMED, "Stay");
        finalEvent = event;
        ActionEvent finalEvent1 = finalEvent;
        assertThrows(NullPointerException.class,()->{
        blackJackUI.actionPerformed(finalEvent1);
        });
    }

    @Test
    void getCardForDealerCardTest(){
        blackJackUI.getCardForDealerCard(new CardObject("Acs","King"));
        blackJackUI.getCardForDealerCard(new CardObject("Acs","Jack"));
        blackJackUI.getCardForDealerCard(new CardObject("Acs","Queen"));
        blackJackUI.getCardForDealerCard(new CardObject("Acs","Ace"));
        blackJackUI.getCardForDealerCard(new CardObject("Acs","1"));

        blackJackUI.showWinMsg(graphics,"Test");
        blackJackUI.setBGImg(graphics);
        blackJackUI.setFieldsNull();
    }
}