package com.cognizant.tweeterapp.tweeterapp.service;

import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import com.cognizant.tweeterapp.tweeterapp.model.UserTweetReplied;

import java.util.List;

public interface UserService {

    public void saveUser(User user);

    User findUserByUserId(String userId);

    public List<User> getAllUsers();

    public List<User> getUsersByLoginId(String loginId);

    public User getUserByLoginId(String loginId);

    public User getUserByEmail(String email);

    public User updatePassword(User user, String newPassword);

    public User getUserByLoginIdAndPassword(String loginId, String password);

    List<Tweet> getAllTweets(String loginId);

    int tweetLikedByUser(String loginId, String tweetId);

    UserTweetReplied tweetRepliedByUser(String repliedMessage, String loginId, String tweetId);
}
