package com.example.demo.helper;

import java.util.List;

public class PasswordManager {
    public static String hashPassword(String password, String email) {

        String raw = password + email;
        int len = email.length();
        char[] rawArray = raw.toCharArray();
        for ( int i = 0; i < rawArray.length; i++ ) {
            rawArray[i] = (char)(rawArray[i] + len);
        }

        return new String(rawArray);
    }

    public static boolean verifyPassword(String password, String hashedPassword, String email) {
        return hashPassword(password,email).equals(hashedPassword);
    }
}
