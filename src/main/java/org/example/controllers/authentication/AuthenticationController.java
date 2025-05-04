package org.example.controllers.authentication;

import org.example.models.Result;

import java.util.regex.Matcher;

public class AuthenticationController {

    public Result loginInput(Matcher matcher){return null;}

    private Result login(Matcher matcher){return null;}

    public Result registerInput(Matcher matcher){
        if(!matcher.matches()) {
            return new Result(false, "invalid command!");
        }

        String username = matcher.group("username");
        String password = matcher.group("password");
        String passwordConfirm = matcher.group("passwordConfirm");
        String nickname = matcher.group("nickname");
        String email = matcher.group("email");
        String gender = matcher.group("gender");

        if(AuthenticationValidator.usernameExists(username)){
            return new Result(false, "Username already exists!");
        }
        if(!username.matches("^[a-zA-Z0-9-]+$")){
            return new Result(false, "Invalid username format!");
        }
        if(!email.matches("^[a-zA-Z0-9][a-zA-Z0-9._-]*@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$")){
            return new Result(false, "Invalid email format!");
        }
        if(!password.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{ };:'\",.<>?/\\\\|`~]$")){
            return new Result(false, "Invalid password format!");
        }
        if(password.length() < 8){
            return new Result(false, "password must be at least 8 characters!");
        }
        if (!password.matches(".*[a-z].*")) {
            return new Result(false, "Password must contain at least one lowercase letter!");
        }
        if (!password.matches(".*[A-Z].*")) {
            return new Result(false, "Password must contain at least one uppercase letter!");
        }
        if (!password.matches(".*\\d.*")) {
            return new Result(false, "Password must contain at least one digit!");
        }
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{ };:'\",.<>?/\\\\|`~].*")) {
            return new Result(false, "Password must contain at least one special character!");
        }
        if (!password.equals(passwordConfirm)) {
            return new Result(false, "Passwords do not match!");
        }


        return null;
    }

    private Result register(Matcher matcher){return null;}

    public Result forgotPassword(Matcher matcher){return null;}


    public Result generateHash(Matcher matcher){return null;}

    public Result giveSecurityQuestion(Matcher matcher){return null;}

    public Result answerSecurityQuestion(Matcher matcher){return null;}
}
