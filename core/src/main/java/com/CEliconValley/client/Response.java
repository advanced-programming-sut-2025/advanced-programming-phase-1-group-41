package com.CEliconValley.client;

import com.CEliconValley.client.view.MainMenuView;
import com.CEliconValley.common.UserData;
import com.CEliconValley.common.messages.ErrorMessage;
import com.CEliconValley.common.messages.SuccessMessage;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Menu;
import com.CEliconValley.client.view.AuthenticationMenuView;
import com.CEliconValley.client.view.ProfileMenuView;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.google.gson.Gson;

public class Response {
    static Gson gson = new Gson();
    public static void successResponse(SuccessMessage message) {
        switch (message.type) {
            case "login_request" -> {
                UserData userData = gson.fromJson(message.success, UserData.class);
                AppClient.login(userData);
            }
            case "forgotpass_request" -> {
                AuthenticationMenuView screen = (AuthenticationMenuView) AppClient.getMenu().getScreen();
                screen.forgotSubmitButton.getLabel().setText("Submit");
                UserData userData = gson.fromJson(message.success, UserData.class);
                screen.securityQuestionLabel.setText(userData.getQuestion());
                screen.securityQuestionLabel.setColor(Color.YELLOW);
            }
            case "fp_request" -> {
                AuthenticationMenuView screen = (AuthenticationMenuView) AppClient.getMenu().getScreen();
                screen.forgotSubmitButton.getLabel().setText("Show Security Question");
                screen.securityQuestionLabel.setText("Your Security Question");
                screen.securityQuestionLabel.setColor(Color.WHITE);
                screen.switchForm("login");
                screen.setMessage("Password Changed Successfully.", Color.LIME);
            }
            case "prereg_request" -> {
                AuthenticationMenuView screen = (AuthenticationMenuView) AppClient.getMenu().getScreen();
                screen.setMessage("", Color.CLEAR);
                screen.switchForm("securityQuestion");
            }
            case "register_request" -> {
                AuthenticationMenuView screen = (AuthenticationMenuView) AppClient.getMenu().getScreen();
                screen.controller.securityAnswer = "";
                screen.controller.securityQuestion = "";
                screen.switchForm("register");
                screen.setMessage("User Registered Successfully. ✅", Color.LIME);
            }
            case "profile_request" -> {
                if(message.success.equals("delete")) {
                    Gdx.app.postRunnable(() -> {
                        Menu.Authentication.resetMenu();
                        AppClient.setMenu(Menu.Authentication);
                    });
                }else{
                    ProfileMenuView screen = (ProfileMenuView) AppClient.getMenu().getScreen();
                    screen.setMessage("Profile updated Successfully.", Color.LIME);
                    AppClient.setUserData(gson.fromJson(message.success, UserData.class));
                    screen.updateInfo();
                }
            }
        }
    }

    public static void errorResponse(ErrorMessage message) {
        switch (message.type) {
            case "login_request", "forgotpass_request", "fp_request","prereg_request"-> {
                AuthenticationMenuView screen = (AuthenticationMenuView) AppClient.getMenu().getScreen();
                screen.setMessage(message.error, Color.RED);
            }
            case "profile_request" ->{
                ProfileMenuView screen = (ProfileMenuView) AppClient.getMenu().getScreen();
                screen.setMessage(message.error, Color.RED);
            }
            case "join-lobby" -> {
                Gdx.app.postRunnable(() -> {
                    if(AppClient.getMenu().getScreen() instanceof  MainMenuView view) {
                        view.getJoinLobbyMessage().setText(message.error);
                        System.out.println("set the error " + message.error);
                    }
                });
            }
        }
    }
}
