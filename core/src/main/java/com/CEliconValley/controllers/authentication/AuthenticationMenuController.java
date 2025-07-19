package com.CEliconValley.controllers.authentication;

import com.CEliconValley.Main;
import com.CEliconValley.models.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.CEliconValley.views.AuthenticationMenuView;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AuthenticationMenuController {

    private AuthenticationMenuView view;
    private String selectedGender = "male";
    private String securityQuestion = "";
    private String securityAnswer = "";
    private String password;
    private String username;
    private String nickname;
    private String email;

    public void setView(AuthenticationMenuView view) {
        this.view = view;
    }

    public void setupListeners() {

        // --- Switch Tabs ---
        view.getRegisterTab().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.switchForm("register");
                view.setMessage("", Color.CLEAR);
            }
        });

        view.getLoginTab().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.switchForm("login");
                view.setMessage("", Color.CLEAR);
            }
        });

        view.getForgotTab().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.switchForm("forgot");
                view.setMessage("", Color.CLEAR);
            }
        });

        // --- Gender Selection ---
        view.genderMaleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedGender = "male";
                view.genderMaleButton.setColor(Color.GREEN);
                view.genderFemaleButton.setColor(Color.WHITE);
            }
        });

        view.genderFemaleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedGender = "female";
                view.genderFemaleButton.setColor(Color.GREEN);
                view.genderMaleButton.setColor(Color.WHITE);
            }
        });

        // --- Register Submit ---
        view.regSubmitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleRegister();
            }
        });

        view.randomPasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                generateRandomPassword();
            }
        });

        // --- Login Submit ---
        view.loginSubmitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    handleLogin();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // --- Forgot Password Submit ---
        view.forgotSubmitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    handleForgotPassword();
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // --- Security Questions ---
        for(TextButton button : view.securityQuestions){
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    for(TextButton button2 : view.securityQuestions){
                        if(!button2.getText().equals(button.getText())){
                            button.setColor(Color.LIME);
                            button.getLabel().setColor(Color.CYAN);
                            button2.setColor(Color.GRAY);
                            button2.getLabel().setColor(Color.GRAY);
                            securityQuestion = button.getLabel().getText().toString();
//                            view.setMessage("Your question: " + button.getLabel().getText().toString(), Color.RED);
                        }
                    }
                }
            });
        }

        // --- Security Submit ---
        view.securitySubmitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                securityAnswer = view.securityAnswer.getText();
                if(securityAnswer == null || securityAnswer.isEmpty()){
                    view.setMessage("Fill The Answer!", Color.RED);
                    return;
                }
                if(securityQuestion.isEmpty()){
                    view.setMessage("Select A Question!", Color.RED);
                    return;
                }

                Gender gender = Gender.Male;
                if(selectedGender.equals("female")){
                    gender = Gender.Female;
                }
                try {
                    register(gender);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                view.regUsername.setText("");
                view.regEmail.setText("");
                view.regNickname.setText("");
                view.regPassword.setText("");
                view.regConfirmPassword.setText("");
                view.switchForm("register");
                view.setMessage("User Registered Successfully. ✅", Color.LIME);
            }
        });
    }

    private void handleRegister() {
        username = view.regUsername.getText();
        password = view.regPassword.getText();
        String confirm = view.regConfirmPassword.getText();
        nickname = view.regNickname.getText();
        email = view.regEmail.getText();

        //Validations
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty() ||
            nickname.isEmpty() || email.isEmpty() || (!view.genderFemaleButton.isChecked() && !view.genderMaleButton.isChecked())) {
            view.setMessage("Please fill all fields.", Color.RED);
            return;
        }
        if(AuthenticationValidator.usernameExists(username)){
            view.setMessage("Username already exists!", Color.RED);
            return;
        }
        if(!username.matches("^[a-zA-Z0-9-]{1,8}$")){
            view.setMessage("Invalid username format!", Color.RED);
            return;
        }
        if(!email.matches("^[a-zA-Z0-9][a-zA-Z0-9_-]*\\.?[a-zA-Z0-9_-]*[a-zA-Z0-9]@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$")){
            view.setMessage("Invalid email format!", Color.RED);
            return;
        }
        if(!AuthenticationValidator.passwordValidation(password, view)){
            return;
        }
        if (!password.equals(confirm)) {
            view.setMessage("Passwords do not match!", Color.RED);
            return;
        }

        view.setMessage("", Color.CLEAR);
        view.switchForm("securityQuestion");

        // اینجا می‌تونی کاربر رو بسازی یا بفرستی به سرور
        System.out.println(">> REGISTER:");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Nickname: " + nickname);
        System.out.println("Email: " + email);
        System.out.println("Gender: " + selectedGender);
    }

    private void handleLogin() throws NoSuchAlgorithmException {
        String username = view.loginUsername.getText();
        String password = view.loginPassword.getText();
        boolean stayLoggedIn = view.stayLoggedInCheckbox.isChecked();

        if (username.isEmpty() || password.isEmpty()) {
            view.setMessage("Please enter username and password.", Color.RED);
            return;
        }
        if(!AuthenticationValidator.usernameExists(username)){
            view.setMessage("Username does not exist!", Color.RED);
            return;
        }
        User user = Finder.getUserByUsername(username);
        String passHash = getHash(password);
        assert user != null;
        if(!passHash.equals(user.getPassword())){
            view.setMessage("Password does not match!", Color.RED);
            return;
        }
        login(user, stayLoggedIn);

        // مثال لاگین
        view.setMessage("Login attempted. (dummy logic)", Color.YELLOW);
        System.out.println(">> LOGIN:");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Stay Logged In: " + stayLoggedIn);
    }

    private void handleForgotPassword() throws NoSuchAlgorithmException {
        if(view.forgotSubmitButton.getLabel().getText().toString().equals("Show Security Question")){
            String username = view.forgotUsername.getText();
            if(username.isEmpty()){
                view.setMessage("Please fill username field.", Color.RED);
                return;
            }
            if(!AuthenticationValidator.usernameExists(username)){
                view.setMessage("Username does not exist!", Color.RED);
                return;
            }
            view.forgotSubmitButton.getLabel().setText("Submit");
            view.securityQuestionLabel.setText(Finder.getUserByUsername(username).getQuestion());
            view.securityQuestionLabel.setColor(Color.YELLOW);
            return;
        }

        String username = view.forgotUsername.getText();
        String answer = view.forgotAnswer.getText();
        String newPass = view.newPassword.getText();

        if (username.isEmpty() || answer.isEmpty() || newPass.isEmpty()) {
            view.setMessage("Please fill all fields.", Color.RED);
            return;
        }
        User user = Finder.getUserByUsername(username);
        assert user != null;
        if(!answer.equals(user.getAnswer())){
            view.setMessage("Wrong answer.", Color.RED);
            return;
        }
        if(!AuthenticationValidator.passwordValidation(newPass, view)){
            return;
        }
        user.setPassword(getHash(newPass));

        view.forgotSubmitButton.getLabel().setText("Show Security Question");
        view.securityQuestionLabel.setText("Your Security Question");
        view.securityQuestionLabel.setColor(Color.WHITE);
        view.forgotUsername.setText("");
        view.forgotAnswer.setText("");
        view.newPassword.setText("");
        view.switchForm("login");

        // مثال فراموشی رمز
        view.setMessage("Password Changed Successfully.", Color.LIME);
        System.out.println(">> FORGOT PASSWORD:");
        System.out.println("Username: " + username);
        System.out.println("Answer: " + answer);
        System.out.println("New Password: " + newPass);
    }

    public String getSelectedGender() {
        return selectedGender;
    }

    private void generateRandomPassword() {
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
        view.setMessage("Generated Password: " + password, Color.BLUE);
        view.regPassword.setText(password);
        view.regConfirmPassword.setText(password);
    }

    private void register(Gender gender) throws NoSuchAlgorithmException {
        password = getHash(password);
        User user = new User(username, password, email, nickname, gender, securityQuestion, securityAnswer);
        saveUser(user);
        App.addUser(user);
        securityAnswer = "";
        securityQuestion = "";
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
    private void login(User user, boolean stayLoggedIn){
        user.setStayLoggedIn(stayLoggedIn);
        App.setCurrentUser(user);
        App.setMenu(Menu.Main);
        Main.getMain().setScreen(App.getMenu().getScreen());
    }
    public String getHash(String pass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
