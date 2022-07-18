package com.cognizant.tweeterapp.tweeterapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_tweet_liked")
public class UserTweetLiked {
    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Tweet tweet;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public UserTweetLiked(User user, Tweet tweet) {
        this.user = user;
        this.tweet = tweet;
    }
}
