package com.cognizant.tweeterapp.tweeterapp.controller;

import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import com.cognizant.tweeterapp.tweeterapp.service.TweetService;
import com.cognizant.tweeterapp.tweeterapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1.0/tweets")
public class UserTweetController {

    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;

    @Autowired
    @Qualifier("tweetServiceImpl")
    TweetService tweetService;



    public List<Tweet> getAllTweetsOfUser(User user){
        return null;
    }
}
