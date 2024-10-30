package com.blackjack.game.user;

import com.blackjack.game.UI.BeanProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Setter
    private User user = null;

    public UserService(){
        BeanProvider.autowire(this);
    }

    @Getter
    @Setter
    private static String activeUserName = null;

    public boolean validateLoginDetails(Map<String, String> map) {
        String email = map.get("email");
        String password = map.get("password");
        try {
            user = userRepository.findByEmail(email);
            if(null==user){
                throw new Exception("User Not Found!");
            }
            String decryptedText = Encryption.decrypt(user.getPassword());
            if(password.equals(decryptedText)){
                activeUserName = user.getFirstName()+","+user.getLastName();
                return true;
            }else {
                throw new RuntimeException("Password didn't match!");
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public User validateSignUp(Map<String, String> map) {
        try {
            User existingUserCheck = userRepository.findByEmail(map.get("email"));
            if(null!=existingUserCheck){
                throw new RuntimeException("User already Exists with email ID: "+ existingUserCheck.getEmail());
            }
            user = new User();
            user.setEmail(map.get("email"));
            user.setFirstName(map.get("firstName"));
            user.setLastName(map.get("lastName"));
            user.setPassword(Encryption.encrypt(map.get("password")));
            user.setTotalWins(0);
            user.setTotalLosses(0);
            userRepository.save(user);

            return user;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public User deleteUser(Map<String, String> map) {
        String email = map.get("email");
        User existingUserCheck = userRepository.findByEmail(email);
        if(null==existingUserCheck){
            throw new RuntimeException("User not found with email ID: "+ email);
        }
        userRepository.deleteById(existingUserCheck.getId());
        return existingUserCheck;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getActiveLoggedInUser(){
        return user;
    }

/**    public String sendEmailRequest(String email) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "sandbox.smtp.mailtrap.io");
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sumanthr4179@gmail.com","bqxsrigpxzguasfc");
            }
        });
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(email));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            msg.setSubject("Password Reset Code!");
            msg.setText("Dummy Code");

            Transport.send(msg);
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }*/
}
