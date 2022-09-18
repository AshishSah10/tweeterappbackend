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

    private int likeCount = 0;

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date tweetCreatedDateTime;




    //@DocumentReference(lookup="{'users':?#{#self._id} }")
    private String tweetCreatorId;

    public String getTweetCreatorId() {
        return tweetCreatorId;
    }

    public void setTweetCreatorId(String tweetCreatorId) {
        this.tweetCreatorId = tweetCreatorId;
    }
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
                ", tagSet=" + tagSet +
                ", tweetCreatedDateTime=" + tweetCreatedDateTime +
                ", tweetCreatorId='" + tweetCreatorId + '\'' +
                '}';
    }
}
