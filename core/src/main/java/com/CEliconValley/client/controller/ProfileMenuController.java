package com.CEliconValley.client.controller;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.ProfCred;
import com.CEliconValley.models.App;
import com.CEliconValley.Main;
import com.CEliconValley.models.Menu;
import com.CEliconValley.client.view.ProfileMenuView;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.Gson;

public class ProfileMenuController {

    private ProfileMenuView view;

    public void setView(ProfileMenuView view) {
        this.view = view;
    }

    public void setupListeners() {
        view.changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                change("username",view.newUsernameField.getText());
//                handleChangeUsername(view.newUsernameField.getText());
            }
        });
        view.changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                change("password",view.newPasswordField.getText());
//                try {
//                    handleChangePassword(view.newPasswordField.getText());
//                } catch (NoSuchAlgorithmException e) {
//                    throw new RuntimeException(e);
//                }
            }
        });
        view.changeNicknameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                change("nickname",view.newNicknameField.getText());
            }
        });
        view.changeEmailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                change("email",view.newEmailField.getText());
            }
        });


        //TODO Sepehr!
        view.deleteAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                change("delete",":)");
                GameMessage<String> msg = new GameMessage<>("logout_request",
                    AppClient.getUserData().getUsername());
                String json = new Gson().toJson(msg);
                AppClient.getClient().send(json);
            }
        });

        view.backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AppClient.setMenu(Menu.Main);
                Main.getMain().setScreen(AppClient.getMenu().getScreen());
            }
        });
    }
    private void change(String key, String value){
        GameMessage<ProfCred> message =
            new GameMessage<>("profile_request",
                new ProfCred(key,value,AppClient.getUserData()));
        String json = new Gson().toJson(message);
        AppClient.getClient().send(json);
    }
}
