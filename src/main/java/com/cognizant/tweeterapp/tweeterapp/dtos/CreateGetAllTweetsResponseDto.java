package com.cognizant.tweeterapp.tweeterapp.dtos;

import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;

import java.util.List;
import java.util.Map;

public class CreateGetAllTweetsResponseDto {
    private List<UserTweet> userTweetList;

    public CreateGetAllTweetsResponseDto(List<UserTweet> userTweetList) {
        this.userTweetList = userTweetList;
    }

    public List<UserTweet> getUserTweetList() {
        return userTweetList;
    }

    public void setUserTweetList(List<UserTweet> userTweetList) {
        this.userTweetList = userTweetList;
    }
}
