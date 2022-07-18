package com.cognizant.tweeterapp.tweeterapp.RequestWrapper;

import com.cognizant.tweeterapp.tweeterapp.model.User;

public class CreateTweetRequestWrapper {
    User user;
    String message;

    public CreateTweetRequestWrapper(User user, String message) {
        this.user = user;
        this.message = message;
    }
}
