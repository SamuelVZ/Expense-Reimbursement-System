package com.revature.service;

import com.revature.Controller.ExceptionController;
import com.revature.dao.UserDao;
import com.revature.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.FailedLoginException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Base64;

public class UserService {

    public static Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    public UserService(){
        this.userDao = new UserDao();
    }

    public UserService(UserDao mockDao){
        this.userDao = mockDao;
    }

    public User login(String username, String password) throws SQLException, FailedLoginException, NoSuchAlgorithmException {
        logger.info("User service login attempted with username: " + username);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        String passwordHash = hexString.toString();

        System.out.println("password hashed: " + passwordHash);

        System.out.println("The Hashed password: " + passwordHash);


        User user = this.userDao.getUserByUsernameAndPassword(username, passwordHash);

        if(user == null){
            throw new FailedLoginException("Invalid username or password");
        }
        return  user;

    }

}
