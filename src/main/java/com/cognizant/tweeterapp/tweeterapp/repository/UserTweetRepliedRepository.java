package com.cognizant.tweeterapp.tweeterapp.repository;

import com.cognizant.tweeterapp.tweeterapp.model.UserTweetReplied;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserTweetRepliedRepository extends MongoRepository<UserTweetReplied, String> {
    List<UserTweetReplied> findByTweetId(String tweetId);
}
