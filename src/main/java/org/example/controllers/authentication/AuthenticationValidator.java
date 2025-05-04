package org.example.controllers.authentication;

import org.example.models.App;
import org.example.models.User;

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
