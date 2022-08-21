package com.cognizant.tweeterapp.tweeterapp.service.passwordencoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Encrypt implements PasswordEncoder {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Override
    public String encodePassword(String realPassword) {
        return bCryptPasswordEncoder.encode(realPassword);
    }

    @Override
    public boolean matchPassword(String realPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(realPassword, hashedPassword);
    }
}
