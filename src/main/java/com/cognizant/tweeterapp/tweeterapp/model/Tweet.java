package com.cognizant.tweeterapp.tweeterapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;

@Document(collection = "tweet")
public class Tweet {
    @Id
    private String id;

    private String tweetMessage;

    private Set<String> tagSet;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date tweetCreatedDateTime;


    //private User tweetCreator;
    //private Set<User> likedUserSet;
    //private List<ReplyTweet> replyTweetList;


    public String getId() {
        return id;
    }

    public String getTweetMessage() {
        return tweetMessage;
    }



    public Set<String> getTagSet() {
        return tagSet;
    }

    public Date getTweetCreatedDateTime() {
        return tweetCreatedDateTime;
    }

    public void setTweetMessage(String tweetMessage) {
        this.tweetMessage = tweetMessage;
    }

    public void setTagSet(Set<String> tagSet){
        this.tagSet = tagSet;
    }


    public Tweet(String tweetMessage, Set<String> tagSet) {
        this.tweetMessage = tweetMessage;
        this.tweetCreatedDateTime = new Date();
        this.tagSet = tagSet;

    }


    @Override
    public String toString() {
        return "Tweet{" +
                "id='" + id + '\'' +
                ", tweetMessage='" + tweetMessage + '\'' +
                ", tweetCreatedDateTime=" + tweetCreatedDateTime +
                ", tagSet=" + tagSet +
                '}';
    }
}
