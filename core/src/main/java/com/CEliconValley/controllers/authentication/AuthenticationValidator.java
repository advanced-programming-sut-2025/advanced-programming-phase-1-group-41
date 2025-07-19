package com.CEliconValley.controllers.authentication;

import com.CEliconValley.models.App;
import com.CEliconValley.models.User;
import com.CEliconValley.views.AuthenticationMenuView;
import com.badlogic.gdx.graphics.Color;

public class AuthenticationValidator {

    public static boolean usernameExists(String username) {
        for(User user : App.users){
            if(user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    public static boolean passwordValidation(String password, AuthenticationMenuView view) {
        if(!password.matches("^[a-zA-Z0-9!@#$%^&*()+=\\[\\]{}\\-_.;:'`~\",<>?/\\\\|]+$")){
            view.setMessage("Invalid password format!", Color.RED);
            return false;
        }
        if(password.length() < 8){
            view.setMessage("Password must be at least 8 characters!", Color.RED);
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            view.setMessage("Password must contain at least one lowercase letter!", Color.RED);
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            view.setMessage("Password must contain at least one uppercase letter!", Color.RED);
            return false;
        }
        if (!password.matches(".*\\d.*")) {
            view.setMessage("Password must contain at least one digit!", Color.RED);
            return false;
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~].*")) {
            view.setMessage("Password must contain at least one special character!", Color.RED);
            return false;
        }
        return true;
    }
}
