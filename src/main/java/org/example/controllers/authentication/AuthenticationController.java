package org.example.controllers.authentication;

import org.example.models.App;
import org.example.models.Gender;
import org.example.models.Result;
import org.example.models.User;

import java.security.SecureRandom;
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

        String successMessage = matcher.group("User registered successfully!");

        if(AuthenticationValidator.usernameExists(username)){
            return new Result(false, "Username already exists!");
        }
        if(!username.matches("^[a-zA-Z0-9-]+$")){
            return new Result(false, "Invalid username format!");
        }
        if(!email.matches("^[a-zA-Z0-9][a-zA-Z0-9._-]*@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$")){
            return new Result(false, "Invalid email format!");
        }
        if(password.equalsIgnoreCase("random") && passwordConfirm.equalsIgnoreCase("password")) {
            String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
            String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String DIGITS = "0123456789";
            String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{};:'\",.<>?/\\|`~";
            String ALL_CHARS = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARS;
            SecureRandom random = new SecureRandom();
            StringBuilder pass = new StringBuilder();

            pass.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
            pass.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
            pass.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
            pass.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

            for (int i = 4; i < random.nextInt(4) + 9; i++) {
                pass.append(ALL_CHARS.charAt(random.nextInt(ALL_CHARS.length())));
            }
            password = pass.toString();
            successMessage = "User registered successfully!\nYour password is: " + password;
        }
        if(!password.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~]$")){
            return new Result(false, "Invalid password format!");
        }
        if(password.length() < 8){
            return new Result(false, "Password must be at least 8 characters!");
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
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~].*")) {
            return new Result(false, "Password must contain at least one special character!");
        }
        if (!password.equals(passwordConfirm)) {
            return new Result(false, "Passwords do not match!");
        }
        register(username, nickname, email, password, gender);
        return new Result(true, successMessage);

        return null;
    }

    private void register(String username, String nickname, String email, String password, Gender gender){
        int id = 0; //TODO
        App.addUser(new User(id, username, password, email, nickname, gender));
    }

    public Result forgotPassword(Matcher matcher){return null;}


    public Result generateHash(Matcher matcher){return null;}

    public Result giveSecurityQuestion(Matcher matcher){return null;}

    public Result answerSecurityQuestion(Matcher matcher){return null;}
}
