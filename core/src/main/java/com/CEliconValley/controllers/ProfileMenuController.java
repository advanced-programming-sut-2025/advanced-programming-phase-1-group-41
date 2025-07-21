package com.CEliconValley.controllers;

import com.CEliconValley.controllers.authentication.AuthenticationValidator;
import com.CEliconValley.models.App;
import com.CEliconValley.Main;
import com.CEliconValley.models.Menu;
import com.CEliconValley.views.ProfileMenuView;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ProfileMenuController {

    private ProfileMenuView view;

    public void setView(ProfileMenuView view) {
        this.view = view;
    }

    public void setupListeners() {
        view.changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleChangeUsername(view.newUsernameField.getText());
            }
        });
        view.changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    handleChangePassword(view.newPasswordField.getText());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        view.changeNicknameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleChangeNickname(view.newNicknameField.getText());
            }
        });
        view.changeEmailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleChangeEmail(view.newEmailField.getText());
            }
        });


        //TODO Sepehr!
        view.deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        view.backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setMenu(Menu.Main);
                Main.getMain().setScreen(App.getMenu().getScreen());
            }
        });
    }
    private void handleChangeUsername(String username) {
        if(!username.matches("^[a-zA-Z0-9-]{1,8}$")){
            view.setMessage("Invalid username format!", Color.RED);
            return;
        }
        App.getCurrentUser().setUsername(username);
        view.setMessage("Username Changed Successfully!", Color.GREEN);
        view.newUsernameField.setText("");
        view.updateInfo();
    }
    private void handleChangePassword(String password) throws NoSuchAlgorithmException {
        if(!AuthenticationValidator.passwordValidation(password, view)){
            return;
        }
        App.getCurrentUser().setPassword(getHash(password));
        view.setMessage("Password Changed Successfully!", Color.GREEN);
        view.newPasswordField.setText("");
        view.updateInfo();
    }
    private void handleChangeNickname(String nickname) {
        if(!nickname.matches("^[a-zA-Z0-9-]{1,8}$")){
            view.setMessage("Invalid nickname format!", Color.RED);
            return;
        }
        App.getCurrentUser().setNickname(nickname);
        view.setMessage("Nickname Changed Successfully!", Color.GREEN);
        view.newNicknameField.setText("");
        view.updateInfo();
    }
    private void handleChangeEmail(String email) {
        if(!email.matches("^[a-zA-Z0-9][a-zA-Z0-9_-]*\\.?[a-zA-Z0-9_-]*[a-zA-Z0-9]@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$")){
            view.setMessage("Invalid email format!", Color.RED);
            return;
        }
        App.getCurrentUser().setEmail(email);
        view.setMessage("Email Changed Successfully!", Color.GREEN);
        view.newEmailField.setText("");
        view.updateInfo();
    }
    public String getHash(String pass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
