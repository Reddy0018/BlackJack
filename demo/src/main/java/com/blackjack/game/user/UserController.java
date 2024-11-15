package com.blackjack.game.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "loginApi/v1")
public class UserController {
    @Autowired
    private UserService userService;
    private User user=null;

    @GetMapping(path = "/login")
    public Map<String,Object> login(@RequestBody Map<String,String> map) throws Exception {
        Map<String,Object> response = new HashMap<>();
        try {
            userService.validateLoginDetails(map);
        }catch (Exception e){
            response.put("status", e.getMessage());
            return response;
        }
        response.put("status", "Login Success");
        return response;
    }

    @PostMapping(path = "/SignUp")
    public Map<String,Object> signUp(@RequestBody Map<String,String> map) throws Exception {
        Map<String,Object> response = new HashMap<>();
        try {
            user = userService.validateSignUp(map);
            response.put("user",user);
            response.put("status","SignUp Successfully");

        }catch (Exception e){
            response.put("status",e.getMessage());
            return response;
        }
        return response;
    }

    @DeleteMapping(path = "/deleteUser")
    public Map<String,Object> deleteUser(@RequestBody Map<String,String> map) throws Exception {
        Map<String,Object> response = new HashMap<>();
        try {
            User user = userService.deleteUser(map);
            response.put("userDeleted",user);
            response.put("status","Deleted Successfully");
        }catch (Exception e){
            response.put("status",e.getMessage());
            return response;
        }
        return response;
    }

    @GetMapping(path = "/getUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/logout")
    public String logout(){
        if(null==userService.getActiveLoggedInUser()){
            return "No Active LoggedIn User";
        }
        UserService.setActiveUserName(null);
        return "Logout Successful";
    }

    @GetMapping(path = "/getLoggedInUser")
    public User getLoggenInUser(){
        return userService.getActiveLoggedInUser();
    }
}
