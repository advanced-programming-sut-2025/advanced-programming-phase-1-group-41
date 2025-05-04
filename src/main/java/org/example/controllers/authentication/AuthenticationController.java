package org.example.controllers.authentication;

import org.example.models.Result;

import java.util.regex.Matcher;

public class AuthenticationController {

    public Result loginInput(Matcher matcher){}

    private Result login(Matcher matcher){}

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
            return new Result(false, "username already exists!");
        }
        if(!username.matches("^[a-zA-Z0-9-]+$")){}

    }

    private Result register(Matcher matcher){}

    public Result forgotPassword(Matcher matcher){}


    public Result generateHash(Matcher matcher){}

    public Result giveSecurityQuestion(Matcher matcher){}

    public Result answerSecurityQuestion(Matcher matcher){}
}
