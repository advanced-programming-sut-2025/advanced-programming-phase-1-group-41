package com.CEliconValley.client;

import com.CEliconValley.common.UserData;
import com.CEliconValley.common.messages.ErrorMessage;
import com.CEliconValley.common.messages.SuccessMessage;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.User;
import com.CEliconValley.views.AuthenticationMenuView;
import com.badlogic.gdx.graphics.Color;
import com.google.gson.Gson;

public class Response {
    static Gson gson = new Gson();
    public static void successResponse(SuccessMessage message) {
        switch (message.type) {
            case "login_request" -> {
                System.out.println("here!");
                AppClient.login(message.success);
            }
            case "forgotpass_request" -> {
                AuthenticationMenuView screen = (AuthenticationMenuView) App.getMenu().getScreen();
                screen.forgotSubmitButton.getLabel().setText("Submit");
                UserData userData = gson.fromJson(message.success, UserData.class);
                screen.securityQuestionLabel.setText(userData.getQuestion());
                screen.securityQuestionLabel.setColor(Color.YELLOW);
            }
            case "fp_request" -> {
                AuthenticationMenuView screen = (AuthenticationMenuView) App.getMenu().getScreen();
                screen.forgotSubmitButton.getLabel().setText("Show Security Question");
                screen.securityQuestionLabel.setText("Your Security Question");
                screen.securityQuestionLabel.setColor(Color.WHITE);
                screen.switchForm("login");
                screen.setMessage("Password Changed Successfully.", Color.LIME);
            }
        }
    }

    public static void errorResponse(ErrorMessage message) {
        switch (message.type) {
            case "login_request", "forgotpass_request", "fp_request" -> {
                AuthenticationMenuView screen = (AuthenticationMenuView) App.getMenu().getScreen();
                screen.setMessage(message.error, Color.RED);
            }
        }
    }
}
