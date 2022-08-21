package com.cognizant.tweeterapp.tweeterapp.service;

import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import com.cognizant.tweeterapp.tweeterapp.model.UserTweetLiked;
import com.cognizant.tweeterapp.tweeterapp.model.UserTweetReplied;
import com.cognizant.tweeterapp.tweeterapp.repository.TweetRepository;
import com.cognizant.tweeterapp.tweeterapp.repository.UserRepository;
import com.cognizant.tweeterapp.tweeterapp.repository.UserTweetLikedRepository;
import com.cognizant.tweeterapp.tweeterapp.repository.UserTweetRepliedRepository;
import com.cognizant.tweeterapp.tweeterapp.service.passwordencoder.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{


    private UserRepository userRepository;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserTweetLikedRepository userTweetLikedRepository;

    @Autowired
    private UserTweetRepliedRepository userTweetRepliedRepository;

    @Autowired
    private Encrypt encrypt;



    @Autowired
    public UserServiceImpl(@Qualifier("userRepository") UserRepository theUserRepository) {
        this.userRepository = theUserRepository;
    }

    @Override
    public void saveUser(User user) {
        String hashedPassword = encrypt.encodePassword(user.getPassword());
        user.setNewPassword(hashedPassword);
        this.userRepository.save(user);
    }

    @Override
    public User getUserByLoginIdAndPassword(String loginId, String password) {
        User user = this.userRepository.findUserByLoginId(loginId);
        System.out.println(user);
        if(user != null){
            if(user.getPassword().equals(password)){
                return user;
            }
            else{
                throw new InvalidParameterException("password is incorrect");
            }
        }

        throw new InvalidParameterException("User with "+loginId+" is not found");
    }

    @Override
    public List<Tweet> getAllTweets(String loginId) {
        User user =  userRepository.findByLoginId(loginId).get(0);
        return user.getTweetList();
    }

    @Override
    public int tweetLikedByUser(String loginId, String tweetId) {
        User user = userRepository.findByLoginId(loginId).get(0);
        Tweet tweet = tweetRepository.findById(tweetId).get();

        if(userTweetLikedRepository.findByUserIdAndTweetId(user.getId(), tweet.getId()).size() == 1){
            return userTweetLikedRepository.findByTweetId(tweetId).size();
        }

        UserTweetLiked userTweetLiked = new UserTweetLiked(user, tweet);
        userTweetLikedRepository.save(userTweetLiked);
        return userTweetLikedRepository.findByTweetId(tweetId).size();
    }

    @Override
    public UserTweetReplied tweetRepliedByUser(String repliedMessage, String loginId, String tweetId) {
       User user = userRepository.findByLoginId(loginId).get(0);
       if(user == null){
           throw new InvalidParameterException("user not found");
       }
       Tweet tweet = tweetRepository.findById(tweetId).get();
       if(tweet == null){
           throw new InvalidParameterException("tweet not found");
       }

       Set<String> repliedTagSet = TweetService.generatedTagsFromMessage(repliedMessage);

       UserTweetReplied userTweetReplied = new UserTweetReplied(user, tweet, repliedMessage, repliedTagSet);
       return userTweetRepliedRepository.save(userTweetReplied);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> getUsersByLoginId(String loginId) {
        List<User> filteredUser = this.userRepository.findAllUsersByLoginIdRegx(loginId);
        return filteredUser;
    }

    @Override
    public User getUserByLoginId(String loginId) {
        User user = this.userRepository.findByLoginId(loginId).get(0);
        return user;
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setNewPassword(newPassword);
        this.userRepository.save(user);
        return user;
    }
}
