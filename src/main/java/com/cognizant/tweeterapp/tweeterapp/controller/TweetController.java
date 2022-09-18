package com.cognizant.tweeterapp.tweeterapp.controller;

import com.cognizant.tweeterapp.tweeterapp.TweeterappApplication;

import com.cognizant.tweeterapp.tweeterapp.dtos.CreateGetAllTweetsResponseDto;
import com.cognizant.tweeterapp.tweeterapp.dtos.UserTweet;
import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import com.cognizant.tweeterapp.tweeterapp.model.UserTweetReplied;
import com.cognizant.tweeterapp.tweeterapp.repository.UserRepository;
import com.cognizant.tweeterapp.tweeterapp.service.TweetMessage;
import com.cognizant.tweeterapp.tweeterapp.service.TweetService;
import com.cognizant.tweeterapp.tweeterapp.service.UserService;
import com.cognizant.tweeterapp.tweeterapp.service.UserServiceImpl;
import com.cognizant.tweeterapp.tweeterapp.service.authenticate.Authenticate;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {
    Logger logger = LoggerFactory.getLogger(TweeterappApplication.class);

    private TweetService tweetService;

    @Autowired
    private UserService userService;


    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    @Autowired
    public TweetController(@Qualifier("tweetServiceImpl") TweetService theTweetService) {
        this.tweetService = theTweetService;
    }



    @PostMapping("/all")
    public CreateGetAllTweetsResponseDto getAllTweets(@RequestHeader HttpHeaders requestHeaders){

        logger.info(""+requestHeaders.get("Authorization"));
        String authToken = requestHeaders.get("Authorization").get(0);

        User LoggedInUser = Authenticate.authentication(key, authToken);

        List<Tweet> tweetList = new ArrayList<>();
        tweetList = this.tweetService.getAllTweets();

        List<UserTweet> userTweetList = new ArrayList<>();
        for(Tweet tweet : tweetList){
            logger.info(tweet.getTweetMessage());
            User user = userService.findUserByUserId(tweet.getTweetCreatorId());
            UserTweet userTweet = new UserTweet(user, tweet);
            userTweetList.add(userTweet);
        }

        //logger.info(""+tweetList);
        CreateGetAllTweetsResponseDto responseDto = new CreateGetAllTweetsResponseDto(userTweetList);

        return responseDto;
    }

    @PostMapping("{loginId}/add")
    public Tweet postNewTweet(@RequestHeader HttpHeaders requestHeaders, @PathVariable String loginId, @RequestBody TweetMessage tweetMessage){
        String message = tweetMessage.getTweetMessage();
        String authToken = requestHeaders.get("Authorization").get(0);
        User user = Authenticate.authentication(key, authToken);
        return this.tweetService.createNewTweet(message, loginId);
    }

    @PutMapping("/{loginId}/update/{tweetId}")
    public Tweet updateTweet(@RequestHeader HttpHeaders requestHeaders, @RequestBody TweetMessage newTweetMessage, @PathVariable String tweetId, @PathVariable String loginId){
        String authToken = requestHeaders.get("Authorization").get(0);
        User user = Authenticate.authentication(key, authToken);
        return this.tweetService.updateTweet(newTweetMessage.getTweetMessage(), tweetId, loginId);
    }

    @DeleteMapping("{loginId}/delete/{tweetId}")
    public String deleteTweetById(@RequestHeader HttpHeaders requestHeaders, @PathVariable String loginId, @PathVariable String tweetId){
        String authToken = requestHeaders.get("Authorization").get(0);
        User user = Authenticate.authentication(key, authToken);
        return this.tweetService.deleteTweetById(loginId, tweetId);
    }

    @GetMapping("/welcomeTweetController")
    public String welcome(){
        System.out.println("Nside welcome method");
        return "welcome";
    }

    @PostMapping("/tweetReplies/{tweetId}")
    public List<UserTweetReplied> getRepliesOfTweet(@RequestHeader HttpHeaders requestHeaders, @PathVariable String tweetId){
        String authToken = requestHeaders.get("Authorization").get(0);
        User user = Authenticate.authentication(key, authToken);
        return tweetService.getRepliesOfTweet(tweetId);
    }
}


