package com.cognizant.tweeterapp.tweeterapp.model;

import com.mongodb.DuplicateKeyException;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String firstName;
    private String lastName;

    @Indexed(unique = true)
    private String email;

    @Indexed(unique = true)
    private String loginId;

    private String password;
    private long contactNo;


    //@DocumentReference(lookup="{'tweet':?#{#self._id} }")
   // private List<Tweet> tweetList = new ArrayList<>();
    @DBRef
    private List<Tweet> tweetList = new ArrayList<>();


    private User() {
    }

    public User(String id, String firstName, String lastName, String email, String loginId, String password, long contactNo, List<Tweet> tweetList) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.loginId = loginId;
        this.password = password;
        this.contactNo = contactNo;
        this.tweetList = tweetList;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String hashedPassword){
        this.password = hashedPassword;
    }
    public void setNewPassword(String newPassword){
        this.password = newPassword;
    }

    public long getContactNo() {
        return contactNo;
    }

    public List<Tweet> getTweetList() {
        return tweetList;
    }
    public void addTweetToUserTweetList(Tweet tweet){
        this.tweetList.add(tweet);
    }

    public void setTweetList(List<Tweet> tweetList) {
        this.tweetList = tweetList;
    }

    @Override
    public String toString() {
        return "User{" +
                ", loginId='" + loginId;
    }

    @Override
    public int hashCode() {
        return this.loginId.hashCode();
    }

    public static UserBuilder getUserBuilder(){
        return new UserBuilder();
    }

    public static class UserBuilder{

        private String firstName;

        private String lastName;

        private String email;

        private String loginId;

        private String password;

        private String confirmPassword;

        private long contactNo;

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setLoginId(String loginId) {
            this.loginId = loginId;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
            return this;
        }

        public UserBuilder setContactNo(long contactNo) {
            this.contactNo = contactNo;
            return this;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getLoginId() {
            return loginId;
        }

        public String getPassword() {
            return password;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public long getContactNo() {
            return contactNo;
        }

        public UserBuilder() {
        }

        public User build(){
            // validate all attributes are required
            if(this.firstName.equals("") ){
                throw new InvalidParameterException("first name cannot be null/empty");
            }
            if(this.lastName.equals("")){
                throw new InvalidParameterException("last name cannot be null/ empty");
            }

            // validate passowrd and confirmPassword are same
            if(!this.password.equals(this.confirmPassword)){
                throw new InvalidParameterException("password and confirmPassword are not same");
            }

            // set attribute and return User object
            User user = new User();

            user.firstName = this.firstName;
            user.lastName = this.lastName;
            user.email = this.email;
            user.loginId = this.loginId;
            user.password = this.password;
            user.contactNo = this.contactNo;

            return user;
        }
    }
}
