package com.cognizant.tweeterapp.tweeterapp.repository;

import com.cognizant.tweeterapp.tweeterapp.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{loginId:'?0'}")
    User findUserByLoginId(String loginId);

    @Query("{email:'?0'}")
    User findUserByEmail(String email);



    @Query("{'loginId': {'$regex': ?0}}")
    List<User> findAllUsersByLoginIdRegx(String loginId);

    List<User> findByLoginId(String loginId);

    @Override
    Optional<User> findById(String userId);

}
