package com.cognizant.tweeterapp.tweeterapp.controller;

import com.cognizant.tweeterapp.tweeterapp.TweeterappApplication;
import com.cognizant.tweeterapp.tweeterapp.dtos.PasswordDto;
import com.cognizant.tweeterapp.tweeterapp.exception.LoggedInException;
import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import com.cognizant.tweeterapp.tweeterapp.model.UserCredential;
import com.cognizant.tweeterapp.tweeterapp.model.UserTweetReplied;
import com.cognizant.tweeterapp.tweeterapp.repository.UserRepository;
import com.cognizant.tweeterapp.tweeterapp.service.TweetMessage;
import com.cognizant.tweeterapp.tweeterapp.service.UserService;
import com.cognizant.tweeterapp.tweeterapp.service.authenticate.Authenticate;
import com.cognizant.tweeterapp.tweeterapp.service.passwordencoder.Encrypt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1.0/tweets")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(TweeterappApplication.class);

    private UserService userService;

    @Autowired
    private Encrypt encrypt;

    //@Value("${jwt.secret}")
    //private String key;
    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);



    @Autowired
    public UserController(@Qualifier("userServiceImpl")UserService theUserService) {
        this.userService = theUserService;

    }

    @PostMapping("/register")
    public String registerNewUser(@RequestBody User.UserBuilder userBuilder) throws InvalidParameterException {

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
        return "Success";
    }



    @PostMapping("/login")
    public String login(@RequestBody UserCredential userCredential, HttpServletResponse response) throws InvalidParameterException, IOException {
        User user = userService.getUserByLoginId(userCredential.getLoginId());
        if(user == null){
            logger.info("Invalid username");
            response.setStatus(403);
            response.sendError(403, "Invalid user");
            return null;
        }
        if(!encrypt.matchPassword(userCredential.getRawPassword(), user.getPassword())){
            logger.info("Invalid Password");
            response.setStatus(403);
            response.sendError(403, "Invalid password");
            return null;
        }

        byte[] secret = Base64.getDecoder().decode("lNGXJ9dEpQ9aK3wSvlJQWBMzEJs241fnCVJd4CFi61o=");
        /*String jws = Jwts.builder()
                .claim("user", (User)user)
                .signWith(key)
                .compact();*/

        String jws = Jwts.builder()
                .claim("userId",  user.getId())
                .signWith(Keys.hmacShaKeyFor(secret))
                .compact();


        Cookie cookie = new Cookie("authToken", jws);
        cookie.setPath("/api/v1.0/tweets");
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(9999999);
        //cookie.setSecure(true);


        //logger.info(""+cookie.getSecure());
        //response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "Set-Cookie, cookie, authtoken");
        response.setHeader("Access-Control-Allow-Headers", "Set-Cookie, Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, " +
                "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.addCookie(cookie);
        response.addHeader("Authorization", "Bearer "+jws);

        response.addHeader("authtoken", cookie.getValue());
        logger.info("user: "+user+" successfully loggedIn");
        response.setStatus(200);
        return "Success";
    }

    @PutMapping("/{email}/forgot")
    public String forgotPassword(@RequestBody PasswordDto passwordDto, @PathVariable String email){

        //logger.info(passwordDto.getNewPassword());
        User user = userService.getUserByEmail(email);
        if(user != null){
            userService.updatePassword(user, passwordDto.getNewPassword());
            return "Success";
        }
        return null;

    }

    @GetMapping("/users/all")
    public List<User> getAllUsers(@CookieValue(value = "authToken", defaultValue = "") String authToken) {
        String userId = Authenticate.authentication(key, authToken);

        List<User> userList = userService.getAllUsers();
        return userList;
    }

    @GetMapping("/user/search/{loginId}")
    public List<User> searchUsersByLoginId(@PathVariable String loginId, @CookieValue(value = "authToken", defaultValue = "") String authToken){
        String userId = Authenticate.authentication(key, authToken);
        List<User> userList = userService.getUsersByLoginId(loginId);
        return userList;
    }


    @GetMapping("/{loginId}")
    public List<Tweet> getAllTweetsOfUser(@PathVariable String loginId, @CookieValue(value = "authToken", defaultValue = "") String authToken){
        String userId = Authenticate.authentication(key, authToken);
        return userService.getAllTweets(loginId);
    }


    @PutMapping("{loginId}/like/{tweetId}")
    public int userLikedTweet(@PathVariable String loginId, @PathVariable String tweetId, @RequestHeader HttpHeaders requestHeaders){
        String authToken = requestHeaders.get("Authorization").get(0);
        String userId = Authenticate.authentication(key, authToken);
        return userService.tweetLikedByUser(loginId, tweetId);
    }

    @PostMapping("{loginId}/reply/{tweetId}")
    public UserTweetReplied userRepliedToTweet(@RequestHeader HttpHeaders requestHeaders, @RequestBody TweetMessage repliedMessage, @PathVariable String loginId, @PathVariable String tweetId){
        String authToken = requestHeaders.get("Authorization").get(0);
        String userId = Authenticate.authentication(key, authToken);
        logger.info(""+repliedMessage.getTweetMessage());
        //return null;
        return userService.tweetRepliedByUser(repliedMessage.getTweetMessage(), loginId, tweetId);
    }

    @PostMapping("/logout/{loginId}")
    public String userLogOut(@RequestHeader HttpHeaders requestHeaders, @PathVariable String loginId){
        requestHeaders.remove("Authorization");
        return "Success";
    }

    @GetMapping("/welcomeUserController")
    public String welcome(){
        System.out.println("inside welcome method");
        return "welcome";
    }
}
