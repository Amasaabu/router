package com.example.routing.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encryptor {
    public String encrypt (String strToEncrypt) {
        var hashed = new BCryptPasswordEncoder().encode(strToEncrypt);
        return  hashed;
    }

    public Boolean passwordIsValid(String hashedPassword, String enteredPassword) {
        return new BCryptPasswordEncoder().matches(enteredPassword, hashedPassword);
    }
}
