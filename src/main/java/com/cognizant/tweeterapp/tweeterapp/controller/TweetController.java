package com.cognizant.tweeterapp.tweeterapp.controller;

import com.cognizant.tweeterapp.tweeterapp.TweeterappApplication;
import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import com.cognizant.tweeterapp.tweeterapp.repository.UserRepository;
import com.cognizant.tweeterapp.tweeterapp.service.TweetService;
import com.cognizant.tweeterapp.tweeterapp.service.UserService;
import com.cognizant.tweeterapp.tweeterapp.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {
    Logger logger = LoggerFactory.getLogger(TweeterappApplication.class);

    private TweetService tweetService;


    @Autowired
    public TweetController(@Qualifier("tweetServiceImpl") TweetService theTweetService) {
        this.tweetService = theTweetService;
    }



    @GetMapping("/all")
    public List<Tweet> getAllTweets(){
        return this.tweetService.getAllTweets();
    }

    @PostMapping("{loginId}/add")
    public Tweet postNewTweet(@PathVariable String loginId, @RequestBody String tweetMessage){
        return this.tweetService.createNewTweet(tweetMessage, loginId);
    }

    @PutMapping("/{loginId}/update/{tweetId}")
    public Tweet updateTweet(@RequestBody String newTweetMessage, @PathVariable String tweetId, @PathVariable String loginId){
        return this.tweetService.updateTweet(newTweetMessage, tweetId, loginId);
    }

    @DeleteMapping("{loginId}/delete/{tweetId}")
    public String deleteTweetById(@PathVariable String loginId, @PathVariable String tweetId){
        return this.tweetService.deleteTweetById(loginId, tweetId);
    }

    @GetMapping("/welcomeTweetController")
    public String welcome(){
        System.out.println("Nside welcome method");
        return "welcome";
    }
}
