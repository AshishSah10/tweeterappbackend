package com.cognizant.tweeterapp.tweeterapp.service.passwordencoder;

import org.springframework.stereotype.Service;

@Service
public interface PasswordEncoder {
    String encodePassword(String realPassword);

    boolean matchPassword(String realPassword, String hashedPassword);
}
