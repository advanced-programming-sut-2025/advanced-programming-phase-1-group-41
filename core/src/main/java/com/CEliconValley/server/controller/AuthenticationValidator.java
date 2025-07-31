package com.CEliconValley.server.controller;

import com.CEliconValley.models.App;
import com.CEliconValley.models.Result;
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
    public static Result passwordValidation(String password) {
        if(!password.matches("^[a-zA-Z0-9!@#$%^&*()+=\\[\\]{}\\-_.;:'`~\",<>?/\\\\|]+$")){
            return new Result(false,"Invalid password format!");
        }
        if(password.length() < 8){
            return new Result(false,"Password must be at least 8 characters!");
        }
        if (!password.matches(".*[a-z].*")) {
            return new Result(false,"Password must contain at least one lowercase letter!");
        }
        if (!password.matches(".*[A-Z].*")) {
            return new Result(false,"Password must contain at least one uppercase letter!");
        }
        if (!password.matches(".*\\d.*")) {
            return new Result(false,"Password must contain at least one digit!");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~].*")) {
            return new Result(false,"assword must contain at least one special character!");
        }
        return new Result(true,"Password valid!");
    }
}
