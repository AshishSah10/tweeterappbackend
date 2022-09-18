package com.cognizant.tweeterapp.tweeterapp.service.authenticate;

import com.cognizant.tweeterapp.tweeterapp.exception.LoggedInException;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.security.Key;

public class Authenticate  {
    public static User authentication(Key key, String authToken) {
        //if(authToken.equals("")){
        //    throw new LoggedInException("User is not logged in");
        //}
        authToken = authToken.replace("Bearer ","");
        Jws<Claims> jws;
        User user = null;
        try{
            jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);

            Claims claim = jws.getBody();
            user = claim.get("user", User.class);
        }catch (JwtException ex){
            System.out.println("Some problem happen please login again");
            //'throw new LoggedInException("Some problem happen please login again");
        }
        return user;
    }
}
