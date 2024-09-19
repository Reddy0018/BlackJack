package com.blackjack.game.UI;

import com.blackjack.game.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login implements ActionListener {
    private static JLabel userLabel, passwordLabel, success, firstNameLabel, lastNameLabel;
    private static JButton loginButton, signupButton, signUp, switchToLoginButton, startGame;
    private static JPasswordField passwordBox;
    private static JTextField userTextBox, firstNameTextBox, lastNameTextBox;

    private static JFrame jFrame=null;
    private static JPanel jPanel=null;

    @Autowired
    UserService userService;

    /**public static void main(String[] args) {
        Login login = new Login();
        login.createLoginScreen();
    }*/

    public void createLoginScreen() {
        if(null!=jFrame){
            jFrame.dispose();
        }
        jFrame = new JFrame();
        jPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image card = new ImageIcon(Objects.requireNonNull(BlackJackUI.class.getResource("./cards/BlackJack.png"))).getImage();
                g.drawImage(card, 0, 0, null);
            }
        };

        //Frame Creation with specific bounds
        jFrame.setSize(350,400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(jPanel);
        jPanel.setLayout(null);

        // User related UI Code
        userLabel = new JLabel("Email");
        userLabel.setBounds(10,20,80,25);
        userLabel.setForeground(Color.WHITE);
        jPanel.add(userLabel);
        userTextBox = new JTextField(20);
        userTextBox.setBounds(100,20,200,25);
        jPanel.add(userTextBox);

        // Password related UI Code
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10,50,80,25);
        passwordLabel.setForeground(Color.WHITE);
        jPanel.add(passwordLabel);
        passwordBox = new JPasswordField();
        passwordBox.setBounds(100,50,200,25);
        jPanel.add(passwordBox);

        loginButton = new JButton("Login");
        loginButton.setBounds(10,80,80,25);
        loginButton.addActionListener(new Login());
        jPanel.add(loginButton);

        signUp = new JButton("SignUp");
        signUp.setBounds(90,80,80,25);
        signUp.addActionListener(new Login());
        jPanel.add(signUp);

        // Start game UI
        startGame = new JButton("Start game");
        startGame.setBounds(170, 80, 100, 25);
        startGame.addActionListener(this);
        startGame.setActionCommand("startGame");
        startGame.setEnabled(false);
        jPanel.add(startGame);

        success = new JLabel("");
        success.setBounds(10,110,300,25);
        jPanel.add(success);
        jFrame.setVisible(true);
    }

    public void createSignupScreen() {
        jFrame.dispose();

        jFrame = new JFrame();
        jPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image card = new ImageIcon(Objects.requireNonNull(BlackJackUI.class.getResource("./cards/BlackJackSignUp.png"))).getImage();
                g.drawImage(card, 0, 0, null);
            }
        };;
        jFrame.setSize(350,400);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.add(jPanel);
        jPanel.setLayout(null);

        // User related UI Code
        userLabel = new JLabel("Email");
        userLabel.setBounds(10, 20, 80, 25);
        userLabel.setForeground(Color.WHITE);
        jPanel.add(userLabel);
        userTextBox = new JTextField(20);
        userTextBox.setBounds(100, 20, 200, 25);
        jPanel.add(userTextBox);

        firstNameLabel = new JLabel("First Name");
        firstNameLabel.setBounds(10, 50, 80, 25);
        firstNameLabel.setForeground(Color.WHITE);
        jPanel.add(firstNameLabel);
        firstNameTextBox = new JTextField(20);
        firstNameTextBox.setBounds(100, 50, 200, 25);
        jPanel.add(firstNameTextBox);

        lastNameLabel = new JLabel("Last Name");
        lastNameLabel.setBounds(10, 80, 80, 25);
        lastNameLabel.setForeground(Color.WHITE);
        jPanel.add(lastNameLabel);
        lastNameTextBox = new JTextField(20);
        lastNameTextBox.setBounds(100, 80, 200, 25);
        jPanel.add(lastNameTextBox);

        // Password related UI Code
        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 110, 80, 25);
        passwordLabel.setForeground(Color.WHITE);
        jPanel.add(passwordLabel);
        passwordBox = new JPasswordField();
        passwordBox.setBounds(100, 110, 200, 25);
        jPanel.add(passwordBox);

        // Signup Button
        signupButton = new JButton("Sign Up");
        signupButton.setBounds(5, 140, 80, 25);
        signupButton.addActionListener(this);
        signupButton.setActionCommand("Sign Up");
        jPanel.add(signupButton);

        // Switch back to Login Screen
        switchToLoginButton = new JButton("Login");
        switchToLoginButton.setBounds(80, 140, 80, 25);
        switchToLoginButton.addActionListener(this);
        switchToLoginButton.setActionCommand("switchToLogin");
        jPanel.add(switchToLoginButton);

        success = new JLabel("");
        success.setBounds(10, 200, 500, 25);
        jPanel.add(success);
        jFrame.setVisible(true);

        jFrame.revalidate();
        jFrame.repaint();
        jFrame.setVisible(true);
    }

    private boolean checkEmpty(Map<String,String> map){
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String s2 = entry.getValue();
            if (s2.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Login":
                Map<String,String> loginRequestMap = new HashMap<>();
                loginRequestMap.put("email",userTextBox.getText());
                loginRequestMap.put("password",passwordBox.getText());
                try {
                    if(checkEmpty(loginRequestMap)){
                        success.setText("Fields cannot be empty!");
                        success.setForeground(Color.RED);
                        break;
                    }
                    /**if (nameField.getText().isEmpty() || emailField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(MandatoryFieldsExample.this,
                                "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    }*/
                    BeanProvider.autowire(this);
                    boolean loginStatus = userService.validateLoginDetails(loginRequestMap);
                    if(loginStatus){
                        success.setText("Login Success");
                        startGame.setEnabled(true);
                        success.setForeground(Color.GREEN);
                    }else {
                        success.setText("User Not Valid");
                        success.setForeground(Color.RED);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    success.setText(ex.getMessage());
                }
                break;
            case "switchToLogin":
                createLoginScreen();
                break;
            case "Sign Up":
                Map<String,String> signUpRequestMap = new HashMap<>();
                signUpRequestMap.put("email",userTextBox.getText());
                signUpRequestMap.put("password",passwordBox.getText());
                signUpRequestMap.put("firstName",firstNameTextBox.getText());
                signUpRequestMap.put("lastName",lastNameTextBox.getText());
                try {
                    if(checkEmpty(signUpRequestMap)){
                        success.setText("Fields cannot be empty!");
                        success.setForeground(Color.RED);
                        break;
                    }
                    BeanProvider.autowire(this);
                    userService.validateSignUp(signUpRequestMap);
                    success.setText("Sign UP Success");
                    success.setForeground(Color.GREEN);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    success.setText(ex.getMessage());
                }
                break;
            case "SignUp":
                createSignupScreen();
                break;
            case "startGame":
                jFrame.dispose();
                BlackJackUI ui = new BlackJackUI();
                ui.buildBlackJackUI();
                break;
            default:
                break;
        }
    }
}
