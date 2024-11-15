package com.blackjack.game.user;

import com.blackjack.game.UI.BeanProvider;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Encryption encryption;
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
            String decryptedText = encryption.decrypt(user.getPassword());
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
            user.setPassword(encryption.encrypt(map.get("password")));
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
}
