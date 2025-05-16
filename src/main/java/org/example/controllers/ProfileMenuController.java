package org.example.controllers;

import org.example.controllers.authentication.AuthenticationValidator;
import org.example.models.*;

import java.util.regex.Matcher;

public class ProfileMenuController {
    public Result changeUsername(Matcher matcher){
        if(!matcher.matches()){
            return new Result(false, "Invalid command!");
        }
        String username = matcher.group("username");
        if(AuthenticationValidator.usernameExists(username)){
            return new Result(false, "Username already exists!");
        }
        if(!username.matches("^[a-zA-Z0-9-]{1,8}$")){
            return new Result(false, "Invalid username format!");
        }
        App.getCurrentUser().setUsername(username);
        return new Result(true, "Username changed successfully!");
    }

    public Result changePassword(Matcher matcher){
        if(!matcher.matches()){
            return new Result(false, "Invalid command!");
        }
        String newPassword = matcher.group("newPassword");
        String oldPassword = matcher.group("oldPassword");
        if(!App.getCurrentUser().getPassword().equals(oldPassword)){
            return new Result(false, "Wrong password!");
        }
        if(newPassword.equals(oldPassword)){
            return new Result(false, "Passwords are the same!");
        }
        if(!newPassword.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~]+$")){
            return new Result(false, "Invalid password format!");
        }
        if(newPassword.length() < 8){
            return new Result(false, "Password must be at least 8 characters!");
        }
        if (!newPassword.matches(".*[a-z].*")) {
            return new Result(false, "Password must contain at least one lowercase letter!");
        }
        if (!newPassword.matches(".*[A-Z].*")) {
            return new Result(false, "Password must contain at least one uppercase letter!");
        }
        if (!newPassword.matches(".*\\d.*")) {
            return new Result(false, "Password must contain at least one digit!");
        }
        if (!newPassword.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~].*")) {
            return new Result(false, "Password must contain at least one special character!");
        }
        App.getCurrentUser().setPassword(newPassword);
        return new Result(true, "Password changed successfully!");
    }

    public Result changeEmail(Matcher matcher) {
        if(!matcher.matches()){
            return new Result(false, "Invalid command!");
        }
        String email = matcher.group("email");
        if(!email.matches("^[a-zA-Z0-9][a-zA-Z0-9._-]*@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$")){
            return new Result(false, "Invalid email format!");
        }
        App.getCurrentUser().setEmail(email);
        return new Result(true, "Email changed successfully!");
    }

    public Result changeNickname(Matcher matcher) {
        if(!matcher.matches()){
            return new Result(false, "Invalid command!");
        }
        String nickname = matcher.group("nickname");
        App.getCurrentUser().setNickname(nickname);
        return new Result(true, "Nickname changed successfully!");
    }

    public Result userInfo() {
        User user = App.getCurrentUser();
        String info = "Username: " + user.getUsername() + "\n" +
                "Nickname: " + user.getNickname() + "\n" +
                "Email: " + user.getEmail() + "\n" +
                "Highest Money: " + user.getHighestScore() + "\n" +
                "Games Count: " + user.getNumberOfGames();

        return new Result(true, info);
    }

    public Result enterMenu(Matcher matcher){
        if(!matcher.matches()) {
            return new Result(false, "Invalid command!");
        }
        String menuName = matcher.group("menuName");
        if(menuName.equalsIgnoreCase("Main")){
            App.setMenu(Menu.Main);
            return new Result(true, "Redirecting to Main Menu...");
        }
        return new Result(false, "Invalid menu name!\nMenu options:\nMain");
    }
}
