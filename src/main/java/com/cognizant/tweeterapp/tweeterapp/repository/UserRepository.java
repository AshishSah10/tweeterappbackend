package com.cognizant.tweeterapp.tweeterapp.repository;

import com.cognizant.tweeterapp.tweeterapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{loginId:'?0'}")
    User findUserByLoginId(String loginId);

    @Query("{'loginId': {'$regex': ?0}}")
    List<User> findAllUsersByLoginIdRegx(String loginId);

    List<User> findByLoginId(String loginId);
}
