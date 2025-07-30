package com.CEliconValley.controllers.authentication;

import com.CEliconValley.Main;
import com.CEliconValley.common.UserData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.*;
import com.badlogic.gdx.graphics.Color;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ServerAuthentication {
    public static Message handleLogin(LoginCred creds) throws NoSuchAlgorithmException {
        String username = creds.username;
        String password = creds.password;
        boolean stayLoggedIn = false;
        if (username.isEmpty() || password.isEmpty()) {
            return new ErrorMessage("login_request","Please enter username and password.");
        }
        if(!AuthenticationValidator.usernameExists(username)){
            return new ErrorMessage("login_request","Username does not exist!");
        }
        User user = Finder.getUserByUsername(username);
        String passHash = getHash(password);
        assert user != null;
        if(!passHash.equals(user.getPassword())){
            return new ErrorMessage("login_request","Password does not match!");
        }
        login(user, stayLoggedIn);
        return new SuccessMessage("login_request",username);
    }
    public static Message showForgotPassword(String username) {
        if(username.isEmpty()){
            return new ErrorMessage("forgotpass_request","Please fill username field.");
        }
        if(!AuthenticationValidator.usernameExists(username)){
            return new ErrorMessage("forgotpass_request","Username does not exist!");
        }
        return new SuccessMessage("forgotpass_request",
            new UserData(Finder.getUserByUsername(username)).toJson());
    }
    public static Message forgotPass(ForgotpassCred cred) {
        String username = cred.username;
        String answer = cred.answer;
        String newPass = cred.newPassword;
        if (username.isEmpty() || answer == null || answer.isEmpty() ||
            newPass == null || newPass.isEmpty()) {
            return new ErrorMessage("fp_request","Please fill all fields.");
        }
        User user = Finder.getUserByUsername(username);
        assert user != null;
        if(!answer.equals(user.getAnswer())){
            return new ErrorMessage("fp_request","Wrong answer.");
        }
        Result validation = AuthenticationValidator.passwordValidation(newPass);
        if(!validation.success()){
            return new ErrorMessage("fp_request",validation.message());
        }
        try{
            user.setPassword(getHash(newPass));
        } catch (Exception e){
            e.printStackTrace();
        }
        UserDB.saveUser(user);
        return new SuccessMessage("fp_request",newPass);
    }


    private static void login(User user, boolean stayLoggedIn){
        user.setStayLoggedIn(stayLoggedIn);
        App.setCurrentUser(user);
        App.setMenu(Menu.Main);
        Menu.Main.resetMenu();
    }
    public static String getHash(String pass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
