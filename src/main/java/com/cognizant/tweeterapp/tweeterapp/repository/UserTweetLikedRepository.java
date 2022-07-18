package com.cognizant.tweeterapp.tweeterapp.repository;


import com.cognizant.tweeterapp.tweeterapp.model.UserTweetLiked;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTweetLikedRepository extends MongoRepository<UserTweetLiked, String> {

    List<UserTweetLiked> findByTweetId(String tweetId);

    List<UserTweetLiked> findByUserIdAndTweetId(String userId, String tweetId);
}
