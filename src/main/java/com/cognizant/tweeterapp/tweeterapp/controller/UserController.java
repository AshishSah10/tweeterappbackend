package com.cognizant.tweeterapp.tweeterapp.controller;

import com.cognizant.tweeterapp.tweeterapp.TweeterappApplication;
import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import com.cognizant.tweeterapp.tweeterapp.model.UserTweetReplied;
import com.cognizant.tweeterapp.tweeterapp.repository.UserRepository;
import com.cognizant.tweeterapp.tweeterapp.service.UserService;
import com.cognizant.tweeterapp.tweeterapp.service.passwordencoder.Encrypt;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/v1.0/tweets")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(TweeterappApplication.class);

    private UserService userService;

    @Autowired
    private Encrypt encrypt;


    @Autowired
    public UserController(@Qualifier("userServiceImpl")UserService theUserService) {
        this.userService = theUserService;
    }

    @PostMapping("/register")
    public User registerNewUser(@RequestBody User.UserBuilder userBuilder) throws InvalidParameterException {

        User newUser = User.getUserBuilder()
                .setFirstName(userBuilder.getFirstName())
                .setLastName(userBuilder.getLastName())
                .setEmail(userBuilder.getEmail())
                .setLoginId(userBuilder.getLoginId())
                .setPassword(userBuilder.getPassword())
                .setConfirmPassword(userBuilder.getConfirmPassword())
                .setContactNo(userBuilder.getContactNo())
                .build();

        userService.saveUser(newUser);
        logger.info("User is successfully registered");
        System.out.println("User is successfully registered");
        return newUser;
    }

    @GetMapping("/login")
    public boolean login(@RequestBody User.UserBuilder userBuilder)throws InvalidParameterException{
        User user = userService.getUserByLoginId(userBuilder.getLoginId());
        if(user == null){
            logger.info("Invalid username");
            return false;
        }
        if(!encrypt.matchPassword(userBuilder.getPassword(), user.getPassword())){
            logger.info("Invalid Password");
        }

        logger.info("user: "+user+" successfully loggedIn");
        return true;
    }

    @GetMapping("{loginId}/forgot")
    public User forgotPassword(@RequestParam String loginId, @RequestParam String newPassword){

        User user = userService.getUserByLoginId(loginId);
        if(user != null){
            userService.updatePassword(user, newPassword);
            return user;
        }
        return null;
    }

    @GetMapping("/users/all")
    public List<User> getAllUsers(){
        List<User> userList = userService.getAllUsers();
        return userList;
    }

    @GetMapping("/user/search/{loginId}")
    public List<User> searchUsersByLoginId(@PathVariable String loginId){
        List<User> userList = userService.getUsersByLoginId(loginId);
        return userList;
    }


    @GetMapping("/{loginId}")
    public List<Tweet> getAllTweetsOfUser(@PathVariable String loginId){
        return userService.getAllTweets(loginId);
    }


    @PutMapping("{loginId}/like/{tweetId}")
    public int userLikedTweet(@PathVariable String loginId, @PathVariable String tweetId){
        return userService.tweetLikedByUser(loginId, tweetId);
    }

    @PostMapping("{loginId}/reply/{tweetId}")
    public UserTweetReplied userRepliedToTweet(@RequestBody String repliedMessage, @PathVariable String loginId, @PathVariable String tweetId){
        return userService.tweetRepliedByUser(repliedMessage, loginId, tweetId);
    }

    @GetMapping("/welcomeUserController")
    public String welcome(){
        System.out.println("inside welcome method");
        return "welcome";
    }
}
