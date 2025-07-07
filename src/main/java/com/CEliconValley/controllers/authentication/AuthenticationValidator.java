package com.CEliconValley.controllers.authentication;

import com.CEliconValley.models.App;
import com.CEliconValley.models.User;

public class AuthenticationValidator {

    public static boolean usernameExists(String username) {
        for(User user : App.users){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
}
