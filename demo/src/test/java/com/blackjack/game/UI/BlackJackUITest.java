package com.blackjack.game.UI;

import com.blackjack.game.user.User;
import com.blackjack.game.user.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.mockito.Mockito.*;

@SpringBootTest
class BlackJackUITest {

    BlackJackUI blackJackUI = new BlackJackUI();

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
    }
}