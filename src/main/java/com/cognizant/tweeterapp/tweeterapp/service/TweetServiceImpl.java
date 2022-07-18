package com.cognizant.tweeterapp.tweeterapp.service;

import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import com.cognizant.tweeterapp.tweeterapp.model.UserTweetLiked;
import com.cognizant.tweeterapp.tweeterapp.model.UserTweetReplied;
import com.cognizant.tweeterapp.tweeterapp.repository.TweetRepository;
import com.cognizant.tweeterapp.tweeterapp.repository.UserRepository;
import com.cognizant.tweeterapp.tweeterapp.repository.UserTweetLikedRepository;
import com.cognizant.tweeterapp.tweeterapp.repository.UserTweetRepliedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import javax.management.InvalidAttributeValueException;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TweetServiceImpl implements TweetService {
    @Autowired
    @Qualifier("tweetRepository")
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTweetLikedRepository userTweetLikedRepository;

    @Autowired
    private UserTweetRepliedRepository userTweetRepliedRepository;

    @Override
    public Tweet createNewTweet(String tweetMessage, String loginId) {
        if(tweetMessage.length() > 144){
            throw new InvalidParameterException("Tweet should not go beyond 144 characters");
        }
        Set<String> tagSet = new HashSet<>();
        tagSet = TweetService.generatedTagsFromMessage(tweetMessage);
        Tweet tweet = new Tweet(tweetMessage, tagSet);
        User user = userRepository.findByLoginId(loginId).get(0);
        if(user != null){
            tweet = tweetRepository.save(tweet);
            user.addTweetToUserTweetList(tweet);
            userRepository.save(user);
        }
        else{
            throw new InvalidParameterException("no user with loginId found");
        }
        return tweet;
    }

    @Override
    public List<Tweet> getAllTweets() {
        return this.tweetRepository.findAll();
    }

    @Override
    public Tweet updateTweet(String newTweetMessage, String tweetId, String loginId) {
        User user = userRepository.findByLoginId(loginId).get(0);
        boolean foundTweet = false;
        for(Tweet tweet : user.getTweetList()){
            if(tweet.getId().equals(tweetId)){
                foundTweet = true;
            }
        }
        if(!foundTweet){
            System.out.println("This tweet is not created by this user");
            throw new InvalidParameterException("tweet id not found in usersTweetList");
        }
        Set<String> newTagSet = TweetService.generatedTagsFromMessage(newTweetMessage);
        Tweet updatedTweet = tweetRepository.findById(tweetId).get();
        updatedTweet.setTweetMessage(newTweetMessage);
        updatedTweet.setTagSet(newTagSet);
        updatedTweet = tweetRepository.save(updatedTweet);
        return updatedTweet;
    }

    @Override
    public String deleteTweetById(String loginId, String tweetId) {
        User user = userRepository.findByLoginId(loginId).get(0);

        boolean isTweetPresentInUsersList = false;
        // remove tweet from user's list
        for(Tweet tweet : user.getTweetList()){
            if(tweet.getId().equals(tweetId)){
                isTweetPresentInUsersList = true;
                user.getTweetList().remove(tweet);
                userRepository.save(user);
                break;
            }
        }

        if(!isTweetPresentInUsersList){
            System.out.println("tweet is not present in usersTweetList");
            throw new InvalidParameterException("tweet is not present in usersTweetList");
        }
        // remove tweet from user_tweet_liked table(if present)
        for(UserTweetLiked userTweetLiked : userTweetLikedRepository.findByTweetId(tweetId)){
            userTweetLikedRepository.deleteById(userTweetLiked.getId());
        }

        // remove tweet from repliedTweet(if present)
        for(UserTweetReplied userTweetReplied : userTweetRepliedRepository.findByTweetId(tweetId)){
            userTweetRepliedRepository.deleteById(userTweetReplied.getId());
        }

        // remove tweet from tweet
        tweetRepository.deleteById(tweetId);
        System.out.println("Tweet is successfully deleted");
        return "Tweet is successfully deleted";
    }



}
