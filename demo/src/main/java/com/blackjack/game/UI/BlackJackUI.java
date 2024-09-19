package com.blackjack.game.UI;

import com.blackjack.game.blackjack.CardObject;
import com.blackjack.game.blackjack.GameController;
import com.blackjack.game.blackjack.Player;
import com.blackjack.game.user.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class BlackJackUI implements ActionListener {

    private static JFrame blackJackJFrame;
    private static JPanel blackJackPanel=null;
    private static List<Player> players = null;
    private static JLabel dealerTotal, playerTotal;
    private static boolean stay = false;
    private static Image gameBG = new ImageIcon(Objects.requireNonNull(BlackJackUI.class.getResource("./cards/img.png"))).getImage();
    private static JButton hitButton, stayButton, startOver, close;

    private static final GameController controller = new GameController();

    public  void buildBlackJackUI(){
        if(null!=blackJackJFrame){
            blackJackJFrame.dispose();
        }
        blackJackJFrame = new JFrame("Black Jack");
        //blackJackPanel = new JPanel();

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
                    Image card = new ImageIcon(Objects.requireNonNull(BlackJackUI.class.getResource(dealerCard.get(i).getHidden()? "./cards/BackCard.png":dealerCard.get(i).cardPath()))).getImage();
                    graphics.drawImage(card,20 + (110+5)*i,20,110,154,null);
                }
                /** Show Player Cards */
                for(int i=0; i<playerCards.size();i++){
                    Image card = new ImageIcon(Objects.requireNonNull(BlackJackUI.class.getResource(playerCards.get(i).cardPath()))).getImage();
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

        blackJackJFrame.setSize(600,580);
        blackJackJFrame.setLocationRelativeTo(null);
        blackJackJFrame.setResizable(false);
        blackJackJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        blackJackPanel.setLayout(new BorderLayout());
        /**blackJackPanel.setBackground(new Color(53,101,77));
        blackJackPanel.setBackground(Color.white);*/
        blackJackJFrame.add(blackJackPanel);

        dealerTotal = new JLabel("Dealer Total: "+players.get(1).getTotal());
        //dealerTotal.setBounds(10,150,110,80);
        dealerTotal.setForeground(Color.WHITE);
        blackJackPanel.add(dealerTotal,BorderLayout.BEFORE_FIRST_LINE);

        playerTotal = new JLabel("Player Total: "+players.get(0).getTotal());
        //playerTotal.setBounds(180,320,110,80);
        playerTotal.setForeground(Color.WHITE);
        blackJackPanel.add(playerTotal,BorderLayout.SOUTH);


        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        stayButton.setFocusable(false);
        buttonPanel.add(stayButton);
        startOver.setFocusable(false);
        buttonPanel.add(startOver);
        close.setFocusable(false);
        buttonPanel.add(close);
        blackJackJFrame.add(buttonPanel,BorderLayout.SOUTH);
        //dealerCard.forEach(cardObject -> System.out.println(cardObject.cardPath()));

        blackJackJFrame.setVisible(true);

    }

    public static void main(String[] args) {
        BlackJackUI Ui = new BlackJackUI();
        Ui.buildBlackJackUI();
    }

    private void showWinMsg(Graphics graphics, String msg){
        graphics.setFont(new Font("Arial",Font.PLAIN,30));
        graphics.setColor(Color.BLACK);
        graphics.drawString(msg,220,225);
    }

    private void setBGImg(Graphics graphics){
        graphics.drawImage(gameBG, 0, 0, null);
    }

    private void setFieldsNull(){
        blackJackJFrame=null;
        blackJackPanel=null;
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
                //controller.startGame();
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
