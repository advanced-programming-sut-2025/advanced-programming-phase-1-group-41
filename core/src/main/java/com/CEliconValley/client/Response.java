package com.CEliconValley.client;

import com.CEliconValley.common.messages.ErrorMessage;
import com.CEliconValley.common.messages.SuccessMessage;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.User;
import com.CEliconValley.views.AuthenticationMenuView;
import com.badlogic.gdx.graphics.Color;

public class Response {
    public static void successResponse(SuccessMessage message) {
        switch (message.type){
            case "login_request" -> {
                System.out.println("here!");
                AppClient.login(message.success);
            }
        }
    }
    public static void errorResponse(ErrorMessage message) {
        switch (message.type) {
            case "login_request" -> {
                AuthenticationMenuView screen = (AuthenticationMenuView) App.getMenu().getScreen();
                screen.setMessage(message.error, Color.RED);
            }
        }
    }
}
