package com.blackjack.game.UI;

import com.blackjack.game.blackjack.CardObject;
import com.blackjack.game.blackjack.GameController;
import com.blackjack.game.blackjack.Player;
import com.blackjack.game.user.User;
import com.blackjack.game.user.UserController;
import com.blackjack.game.user.UserRepository;
import com.blackjack.game.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class BlackJackUI implements ActionListener {

    private static JFrame blackJackJFrame;
    private static List<Player> players = null;
    private static JLabel dealerTotal, playerTotal;
    private static boolean stay = false;
    private final static Image gameBG = new ImageIcon(Objects.requireNonNull(BlackJackUI.class.getResource("./cards/img.png"))).getImage();
    private static JButton hitButton, stayButton, startOver, close;

    private static final GameController controller = new GameController();
    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;

    public  void buildBlackJackUI(){
        if(null!=blackJackJFrame){
            blackJackJFrame.dispose();
        }
        blackJackJFrame = new JFrame("Black Jack");

        if(null==players){
            players =  controller.startGame();
        }

        List<CardObject> playerCards =  players.get(0).getPlayerCards();
        List<CardObject> dealerCard =  players.get(1).getPlayerCards();

        JPanel blackJackPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics graphics){
                super.paintComponent(graphics);
                setBGImg(graphics);
                /** Show Dealer Cards */
                for(int i=0; i<dealerCard.size();i++){
                    Image card = new ImageIcon(Objects.requireNonNull(BlackJackUI.class.getResource(dealerCard
                            .get(i).getHidden()? "./cards/BackCard.png":dealerCard.get(i).cardPath()))).getImage();
                    graphics.drawImage(card,20 + (110+5)*i,20,110,154,null);
                }
                /** Show Player Cards */
                for(int i=0; i<playerCards.size();i++){
                    Image card = new ImageIcon(Objects.requireNonNull(BlackJackUI.class.getResource(playerCards
                            .get(i).cardPath()))).getImage();
                    graphics.drawImage(card,20 +  (110+5)*i,320,110,154,null);
                }

                if(players.get(0).getBlackjackWin()){
                    stayButton.setEnabled(false);
                    hitButton.setEnabled(false);
                    showWinMsg(graphics,"Player Wins(Black Jack)!");
                }else if(stay){
                    stayButton.setEnabled(false);
                    hitButton.setEnabled(false);
                    if (players.get(0).getBustFlag() || players.get(1).getWinFlag()) {
                        showWinMsg(graphics, "Dealer Wins");
                    } else if(players.get(0).getWinFlag() || players.get(1).getBustFlag()) {
                        showWinMsg(graphics, "Player Wins");
                    }else if (players.get(0).getGameDraw()){
                        showWinMsg(graphics, "Match Tie");
                    }
                }
            }
        };

        if(players.get(0).getBlackjackWin()){
            User user = userController.getLoggenInUser();
            System.out.println(user);
            user.setTotalWins(user.getTotalWins()+1);
            userRepository.save(user);
        }

        JPanel buttonPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                setBGImg(graphics);
            }
        };
        hitButton = new JButton("Hit");
        hitButton.addActionListener(this);
        hitButton.setActionCommand("Hit");

        startOver = new JButton("Start Over");
        startOver.addActionListener(this);
        startOver.setActionCommand("startOver");

        close = new JButton("Close/Logout");
        close.addActionListener(this);
        close.setActionCommand("close");

        stayButton = new JButton("Stay");
        stayButton.addActionListener(this);
        stayButton.setActionCommand("Stay");

        blackJackJFrame.setSize(720,580);
        blackJackJFrame.setLocationRelativeTo(null);
        blackJackJFrame.setResizable(false);
        blackJackJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        blackJackPanel.setLayout(new BorderLayout());
        blackJackJFrame.add(blackJackPanel);

        if(players.get(1).getPlayerCards().get(1).getHidden()){
            dealerTotal = new JLabel("Dealer Total: "+getCardForDealerCard(players
                    .get(1).getPlayerCards().get(0)));
        }else {
            dealerTotal = new JLabel("Dealer Total: "+players.get(1).getTotal());
        }

        dealerTotal.setForeground(Color.WHITE);
        blackJackPanel.add(dealerTotal,BorderLayout.BEFORE_FIRST_LINE);

        playerTotal = new JLabel("Player Total: "+players.get(0).getTotal());
        playerTotal.setForeground(Color.WHITE);
        blackJackPanel.add(playerTotal,BorderLayout.SOUTH);

        BeanProvider.autowire(this);
        User user=userController.getLoggenInUser();
        JTextArea jTextArea = new JTextArea("User Email: "+ user.getEmail() +"\n"+ "Player Total Wins: "+user.getTotalWins()+"\n"+"Player Total Busts: "+user.getTotalLosses());
        jTextArea.setOpaque(false);
        jTextArea.setForeground(Color.WHITE);
        jTextArea.setBounds(450,0,270,80);
        blackJackPanel.add(jTextArea,BorderLayout.EAST);

        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        startOver.setFocusable(false);
        buttonPanel.add(startOver);
        close.setFocusable(false);
        buttonPanel.add(close);
        blackJackJFrame.add(buttonPanel,BorderLayout.SOUTH);
        blackJackJFrame.setVisible(true);
    }

    public String getCardForDealerCard(CardObject cardObject){
        if(cardObject.getCardvalue().contains("Queen")||
                cardObject.getCardvalue().contains("Jack")||
                cardObject.getCardvalue().contains("King")){
            return "10";
        } else if (cardObject.getCardvalue().contains("A")) {
            return "1/11";
        } else {
            return cardObject.getCardvalue();
        }
    }

    public void showWinMsg(Graphics graphics, String msg){
        graphics.setFont(new Font("Arial",Font.PLAIN,30));
        graphics.setColor(Color.WHITE);
        graphics.drawString(msg,220,225);
    }

    public void setBGImg(Graphics graphics){
        graphics.drawImage(gameBG, 0, 0, null);
    }

    public void setFieldsNull(){
        blackJackJFrame=null;
        players = null;
        stay=false;
        hitButton=null;
        stayButton=null;
        startOver=null;
        dealerTotal=null;
        playerTotal=null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Hit":
                controller.playerHitCards();
                buildBlackJackUI();
                break;
            case "Stay":
                stay=true;
                controller.playerHitStandButton();
                buildBlackJackUI();
                break;
            case "startOver":
                blackJackJFrame.dispose();
                setFieldsNull();
                buildBlackJackUI();
                break;
            case "close":
                blackJackJFrame.dispose();
                Login login = new Login();
                login.createLoginScreen();
                setFieldsNull();
                UserService.setActiveUserName(null);
                break;
            default:
                break;
        }
    }
}
