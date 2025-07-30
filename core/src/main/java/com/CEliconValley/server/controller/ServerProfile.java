package com.CEliconValley.server.controller;

import com.CEliconValley.common.UserData;
import com.CEliconValley.common.messages.ErrorMessage;
import com.CEliconValley.common.messages.Message;
import com.CEliconValley.common.messages.SuccessMessage;
import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Result;
import com.CEliconValley.models.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ServerProfile {
    public static Message handleChangeUsername(String username, String oldUsername) {
        if(!username.matches("^[a-zA-Z0-9-]{1,8}$")){
            return new ErrorMessage("profile_request","Invalid username format!");
        }
        if(AuthenticationValidator.usernameExists(username)){
            return new ErrorMessage("profile_request","Username already exists!");
        }
        User user = Finder.getUserByUsername(oldUsername);
        user.setUsername(username);
        UserDB.saveUser(user);
        return new SuccessMessage("profile_request",new UserData(user).toJson());
    }
    public static Message handleChangePassword(String password, String username) throws NoSuchAlgorithmException {
        Result result = AuthenticationValidator.passwordValidation(password);
        if(!result.success()){
            return new ErrorMessage("profile_request",result.message());
        }
        User user = Finder.getUserByUsername(username);
        user.setPassword(getHash(password));
        UserDB.saveUser(user);
        return new SuccessMessage("profile_request",new UserData(user).toJson());
    }
    public static Message handleChangeNickname(String nickname, String username) {
        if(!nickname.matches("^[a-zA-Z0-9-]{1,8}$")){
            return new ErrorMessage("profile_request","Invalid nickname format!");
        }
        User user = Finder.getUserByUsername(username);
        user.setNickname(nickname);
        UserDB.saveUser(user);
        return new SuccessMessage("profile_request",new UserData(user).toJson());
    }
    public static Message handleChangeEmail(String email, String username) {
        if(!email.matches("^[a-zA-Z0-9][a-zA-Z0-9_-]*\\.?[a-zA-Z0-9_-]*[a-zA-Z0-9]@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$")){
            return new ErrorMessage("profile_request","Invalid email format!");
        }
        User user = Finder.getUserByUsername(username);
        user.setEmail(email);
        UserDB.saveUser(user);
        return new SuccessMessage("profile_request",new UserData(user).toJson());
    }
    public static Message delete(String username){
        App.setCurrentUser(null);
        User user = Finder.getUserByUsername(username);
        App.getUsers().remove(user);
        UserDB.deleteUser(user);
        return new SuccessMessage("profile_request","delete");
    }
    public static String getHash(String pass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
