package com.cognizant.tweeterapp.tweeterapp.service;

import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface TweetService {
    Tweet createNewTweet(String tweetMessage, String loginId);

    public List<Tweet> getAllTweets();

    public Tweet updateTweet(String newTweetMessage, String tweetId, String loginId);

    String deleteTweetById(String loginId, String tweetId);

    public static Set<String> generatedTagsFromMessage(String tweetMessage){
        Set<String> tagSet = new HashSet<>();
        String[] stringArray = tweetMessage.split(" ");
        for(int i = 0; i < stringArray.length; i++){
            String word = stringArray[i];
            if(word.startsWith("#")){
                if(word.length() > 50){
                    throw new InvalidParameterException("tag length cannot go beyond 50 character");
                }
                tagSet.add(word);
            }
        }
        return tagSet;
    }
}
