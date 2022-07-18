package com.cognizant.tweeterapp.tweeterapp.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class ReplyTweet {
    @DBRef
    private User repliedUser;
    private String repliedMessage;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date repliedDateTime;

    private Tweet repliedToTweet;

    public ReplyTweet(User repliedUser, String repliedMessage, Tweet repliedToTweet) {
        this.repliedUser = repliedUser;
        this.repliedMessage = repliedMessage;
        this.repliedDateTime = new Date();
        this.repliedToTweet = repliedToTweet;
    }
}
