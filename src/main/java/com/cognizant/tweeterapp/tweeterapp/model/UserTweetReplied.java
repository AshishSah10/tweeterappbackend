package com.cognizant.tweeterapp.tweeterapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Document(collection = "user_tweet_replied")
public class UserTweetReplied {
    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Tweet tweet;


    private String repliedMessage;

    private Set<String> repliedTagSet;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date repliedDateTime;

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

    public String getRepliedMessage() {
        return repliedMessage;
    }

    public void setRepliedMessage(String repliedMessage) {
        this.repliedMessage = repliedMessage;
    }

    public Set<String> getRepliedTagSet() {
        return repliedTagSet;
    }

    public void setRepliedTagSet(Set<String> repliedTagSet) {
        this.repliedTagSet = repliedTagSet;
    }

    public Date getRepliedDateTime() {
        return repliedDateTime;
    }

    public void setRepliedDateTime(Date repliedDateTime) {
        this.repliedDateTime = repliedDateTime;
    }

    public UserTweetReplied(User user, Tweet tweet, String repliedMessage, Set<String> repliedTagSet) {
        this.user = user;
        this.tweet = tweet;
        this.repliedMessage = repliedMessage;
        this.repliedTagSet = repliedTagSet;
        this.repliedDateTime = new Date();
    }
}
