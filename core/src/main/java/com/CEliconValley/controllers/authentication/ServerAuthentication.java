package com.CEliconValley.controllers.authentication;

import com.CEliconValley.Main;
import com.CEliconValley.common.messages.ErrorMessage;
import com.CEliconValley.common.messages.LoginCred;
import com.CEliconValley.common.messages.Message;
import com.CEliconValley.common.messages.SuccessMessage;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Menu;
import com.CEliconValley.models.User;
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
        if(!AuthenticationValidator.usernameExists(username)){
            System.out.println("here!!");
            return new ErrorMessage("login_request","Username does not exist!");
//            view.setMessage("Username does not exist!", Color.RED);
//            return;
        }
        User user = Finder.getUserByUsername(username);
        String passHash = getHash(password);
        assert user != null;
        if(!passHash.equals(user.getPassword())){
            return new ErrorMessage("login_request","Password does not match!");
//            view.setMessage("Password does not match!", Color.RED);
//            return;
        }
        login(user, stayLoggedIn);
        return new SuccessMessage("login_request",username);
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
