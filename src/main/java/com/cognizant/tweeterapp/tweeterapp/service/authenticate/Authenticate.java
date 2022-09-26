package com.cognizant.tweeterapp.tweeterapp.service.authenticate;

import com.cognizant.tweeterapp.tweeterapp.exception.LoggedInException;
import com.cognizant.tweeterapp.tweeterapp.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

public class Authenticate  {
    public static String authentication(Key key, String authToken) {
        //if(authToken.equals("")){
        //    throw new LoggedInException("User is not logged in");
        //}
        authToken = authToken.replace("Bearer ","");
        Jws<Claims> jws;
        String userId = null;
        byte[] secret = Base64.getDecoder().decode("lNGXJ9dEpQ9aK3wSvlJQWBMzEJs241fnCVJd4CFi61o=");

        try{
            /*jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);*/

            jws = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secret)).parseClaimsJws(authToken);

            Claims claim = jws.getBody();

            userId = (String)claim.get("userId");
            //user = (User)claim.get("user");
        }catch (JwtException ex){
            ex.printStackTrace();
            System.out.println("Some problem happen please login again");
            //throw new LoggedInException("Some problem happen please login again");
        }
        return userId;
    }
}
