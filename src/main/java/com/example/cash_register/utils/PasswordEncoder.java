package com.example.cash_register.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public static String encode(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean passwordMatches (String password, String encodedPassword){
        return BCrypt.checkpw(password, encodedPassword);
    }
}
