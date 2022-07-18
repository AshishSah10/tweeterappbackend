package com.cognizant.tweeterapp.tweeterapp.repository;

import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TweetRepository extends MongoRepository<Tweet, String> {

}
