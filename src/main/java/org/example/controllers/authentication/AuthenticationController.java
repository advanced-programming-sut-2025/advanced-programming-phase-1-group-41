package org.example.controllers.authentication;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.example.database.UserDB;
import org.example.models.*;
import org.example.views.commands.AuthenticationCommands;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;
import java.util.regex.Matcher;

public class AuthenticationController {

    public Result loginInput(Matcher matcher) throws NoSuchAlgorithmException {
        if(!matcher.matches()){
            return new Result(false, "Invalid command!");
        }
        String username = matcher.group("username").trim();
        String password = matcher.group("password").trim();
        boolean stayLoggedIn = matcher.group(3) != null;
        if(!AuthenticationValidator.usernameExists(username)){
            return new Result(false, "Username does not exist!");
        }
        User user = Finder.getUserByUsername(username);
        assert user != null;

        password = getHash(password);
        if(!user.getPassword().equals(password)){
            return new Result(false, "Incorrect password! "+
                    password);
        }
        System.out.println(stayLoggedIn);
        login(user, stayLoggedIn);
        return new Result(true, "User logged in successfully!");
    }

    private void login(User user, boolean stayLoggedIn){
        user.setStayLoggedIn(stayLoggedIn);
        App.setCurrentUser(user);
        App.setMenu(Menu.Main);
    }

    public Result registerInput(Matcher matcher, Scanner scanner) throws NoSuchAlgorithmException {
        if(!matcher.matches()) {
            return new Result(false, "Invalid command!");
        }

        String username = matcher.group("username");
        String password = matcher.group("password");
        String passwordConfirm = matcher.group("passwordConfirm");
        String nickname = matcher.group("nickname");
        String email = matcher.group("email");
        String gender = matcher.group("gender");
        Gender genderType = Gender.Male;
        if(gender.equals("male")) {
            genderType = Gender.Male;
        }
        else if(gender.equals("female")) {
            genderType = Gender.Female;
        }

        String successMessage = "User registered successfully!";

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
            password = generateRandomPassword();
            passwordConfirm = password;
            successMessage = "User registered successfully!\nYour password is: " + password;
        }
        if(!password.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~]+$")){
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
        String question, answer;
        System.out.println("Security Questions:");
        for(int i = 0; i < 10; i++){
            System.out.println((i + 1) + ". " + App.questions.get(i));
        }
        while (true){
            String command = scanner.nextLine();
            Matcher newMatcher;
            if((newMatcher = AuthenticationCommands.PickQuestion.getMatcher(command)) != null) {
                ArrayList<String> questionAndAnswer;
                if((questionAndAnswer = giveSecurityQuestion(newMatcher)) != null){
                    question = questionAndAnswer.get(0);
                    answer = questionAndAnswer.get(1);
                    break;
                }
                else{
                    System.out.println("Invalid command!");
                }
            }
            else{
                System.out.println("Invalid command!");
            }
        }
        password = getHash(password);
        register(username, nickname, email, password, genderType, question, answer);
        return new Result(true, successMessage);
    }

    private void register(String username, String nickname, String email, String password,
                          Gender gender, String question, String answer) {
        User user = new User(username, password, email, nickname, gender, question, answer);
        saveUser(user);
        App.addUser(user);
    }

    private void saveUser(User user){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");

        datastore.getMapper().map(TimeLine.class);
        datastore.getMapper().map(Player.class);
        datastore.getMapper().map(Game.class);
        datastore.getMapper().map(User.class);

        datastore.save(user);

    }

    private String generateRandomPassword() {
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
        return pass.toString();
    }

    private ArrayList<String> giveSecurityQuestion(Matcher matcher){
        if(!matcher.matches()) {
            return null;
        }
        int questionNumber = Integer.parseInt(matcher.group("questionNumber"));
        String answer = matcher.group("answer");
        String answerConfirm = matcher.group("answerConfirm");
        if(questionNumber <= 0 || questionNumber > 10){
            return null;
        }
        else if(!answer.equals(answerConfirm)){
            return null;
        }
        ArrayList<String> questionAndAnswer = new ArrayList<>();
        questionAndAnswer.add(App.questions.get(questionNumber - 1));
        questionAndAnswer.add(answer);
        return questionAndAnswer;
    }

    public Result forgotPassword(Matcher matcher, Scanner scanner){
        if(!matcher.matches()) {
            return new Result(false, "Invalid command!");
        }
        String username = matcher.group("username");
        if(!AuthenticationValidator.usernameExists(username)){
            return new Result(false, "username does not exist!");
        }
        User user = Finder.getUserByUsername(username);
        assert user != null;
        System.out.println(user.getQuestion());
        String answer = scanner.nextLine();
        if(!answer.equals(user.getAnswer())){
            return new Result(false, "Wrong answer!");
        }
        System.out.println("Write your new password:");
        answerSecurityQuestion(user, scanner);
        return new Result(true, "Password updated!");
    }

    private void answerSecurityQuestion(User user, Scanner scanner){
        while(true){
            String password = scanner.nextLine();
            if(!password.matches("^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~]+$")){
                System.out.println("Invalid password format!");
            }
            if(password.length() < 8){
                System.out.println("Password must be at least 8 characters!");
            }
            if (!password.matches(".*[a-z].*")) {
                System.out.println("Password must contain at least one lowercase letter!");
            }
            if (!password.matches(".*[A-Z].*")) {
                System.out.println("Password must contain at least one uppercase letter!");
            }
            if (!password.matches(".*\\d.*")) {
                System.out.println("Password must contain at least one digit!");
            }
            if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};:'\",.<>?/\\\\|`~].*")) {
                System.out.println("Password must contain at least one special character!");
            }
            else{
                user.setPassword(password);
                return;
            }
        }
    }

    public String getHash(String pass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
