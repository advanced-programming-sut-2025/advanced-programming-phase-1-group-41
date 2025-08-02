package com.CEliconValley.client.controller;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.CEliconValley.client.view.AuthenticationMenuView;
import com.google.gson.Gson;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AuthenticationMenuController {

    private AuthenticationMenuView view;
    private String selectedGender = "male";
    public String securityQuestion = "";
    public String securityAnswer = "";
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
        view.getExitTab().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
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
                handleLogin();
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
                GameMessage<RegisterCred> message = new GameMessage<>("register_request",
                    new RegisterCred(username, password, email, nickname, gender, securityQuestion, securityAnswer));
                String json = new Gson().toJson(message);
                AppClient.getClient().send(json);
            }
        });
    }

    private void handleRegister() {
        username = view.regUsername.getText();
        password = view.regPassword.getText();
        String confirm = view.regConfirmPassword.getText();
        nickname = view.regNickname.getText();
        email = view.regEmail.getText();
        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty() ||
            nickname.isEmpty() || email.isEmpty() || (!view.genderFemaleButton.isChecked() && !view.genderMaleButton.isChecked())) {
            view.setMessage("Please fill all fields.", Color.RED);
            return;
        }
        GameMessage<PreRegisterCred> message = new GameMessage<>("prereg_request",
            new PreRegisterCred(username, password, confirm, nickname, email));
        String json = new Gson().toJson(message);
        AppClient.getClient().send(json);

    }

    private void handleLogin(){
        try {
            LoginCred credentials = new LoginCred(view.loginUsername.getText(), view.loginPassword.getText());
            GameMessage<LoginCred> message = new GameMessage<>("login_request", credentials);
            String json = new Gson().toJson(message);
            AppClient.getClient().send(json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void handleForgotPassword() throws NoSuchAlgorithmException {
        if(view.forgotSubmitButton.getLabel().getText().toString().equals("Show Security Question")){
            String username = view.forgotUsername.getText();
            try {
                GameMessage<String> message = new GameMessage<>("forgotpass_request", username);
                String json = new Gson().toJson(message);
                AppClient.getClient().send(json);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }

        String username = view.forgotUsername.getText();
        String answer = view.forgotAnswer.getText();
        String newPass = view.newPassword.getText();
        GameMessage<ForgotpassCred> message = new GameMessage<>("fp_request", new ForgotpassCred(username, answer, newPass));
        String json = new Gson().toJson(message);
        AppClient.getClient().send(json);
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

    public void emptyFields() {
        view.loginPassword.setText("");
        view.loginUsername.setText("");
        view.forgotUsername.setText("");
        view.forgotAnswer.setText("");
        view.newPassword.setText("");
        view.regUsername.setText("");
        view.regEmail.setText("");
        view.regNickname.setText("");
        view.regPassword.setText("");
        view.regConfirmPassword.setText("");
    }

    public void handleEnter() throws NoSuchAlgorithmException {
        if(view.loginForm.isVisible()){
            handleLogin();
        } else if(view.forgotForm.isVisible()){
            handleForgotPassword();
        } else if(view.registerForm.isVisible()){
            handleRegister();
        }
    }
}
