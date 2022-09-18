package com.cognizant.tweeterapp.tweeterapp.dtos;

import com.cognizant.tweeterapp.tweeterapp.model.Tweet;
import com.cognizant.tweeterapp.tweeterapp.model.User;

public class UserTweet {
        private User user;
        private Tweet tweet;

        public UserTweet() {
        }

        public UserTweet(User user, Tweet tweet) {
            this.user = user;
            this.tweet = tweet;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Tweet getTweet() {
            return tweet;
        }

        public void setTweet(Tweet tweet) {
            this.tweet = tweet;
        }
}
