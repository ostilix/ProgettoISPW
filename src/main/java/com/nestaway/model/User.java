package com.nestaway.model;

import com.nestaway.exception.EncryptionException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User{

    private final String username;
    private final String password;

    public User(String username, String password) throws EncryptionException {
        this.username = username;
        this.password = encrypt(password);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean checkPassword(String password) throws EncryptionException {
        return this.password.equals(encrypt(password));
    }

    private String encrypt(String input) throws EncryptionException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder sb = new StringBuilder(no.toString(16));
            while (sb.length() < 128) {
                sb.insert(0, "0");
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptionException("Error in encrypt in user model.", e);
        }
    }

}
